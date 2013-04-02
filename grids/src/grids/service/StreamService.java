package grids.service;

import grids.domain.TweetOnIdComparator;
import grids.entity.Follow;
import grids.entity.Tweet;
import grids.transfer.Stream;
import grids.transfer.TweetCard;
import grids.repos.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class StreamService {
	@Autowired
	private FollowRepos followRepos;
	@Autowired
	private TweetRepos tweetRepos;
	
	public Stream istream(long userId) {
		Stream stream = new Stream(userId);
		
		//XXX consider fetch_size limit
		stream.addAll(tweetRepos.tweets(userId));
		List<Follow> followings = followRepos.followings(userId);
		for (Follow follow : followings) {
			List<Tweet> tweets = tweetRepos.tweets(follow.getTarget().getId(), follow.getTags());
			stream.addAll(tweets);
		}
		higherSort(stream);
		return stream;
	}
	
	private void higherSort(Stream stream) {
		stream.getItems();
		Collections.sort(stream.getItems(), new TweetOnIdComparator());
		//TODO Combine & Pull-near
		combine(stream);
	}

	private void combine(Stream stream) {
		List<CombineGroup> groupSequence = new ArrayList<>();
		for (TweetCard tc : stream.getItems()) {
			if (tc.getOrigin() != null) {
				long originId = tc.getOrigin().getId();
				boolean foundInSeq = false;
				for (CombineGroup group : groupSequence) {
					if (group.originId == originId) {
						group.tweets.add(tc);
						foundInSeq = true;
						break;
					}
				}
				if (!foundInSeq) {
					groupSequence.add(new CombineGroup(originId, tc));
				}
			}
			else {
				groupSequence.add(new CombineGroup(tc));
			}
		}
		//XXX Flatten
		stream.getItems().clear();
		for (CombineGroup group : groupSequence) {
			stream.getItems().addAll(group.tweets);
			if (group.origin != null) {stream.getItems().add(group.origin);}
		}
	}
	
	private static class CombineGroup {
		private long originId;
		private TweetCard origin;
		private List<TweetCard> tweets = new ArrayList<>();
		private CombineGroup(long originId, TweetCard first) {
			this.originId = originId;
			tweets.add(first);
		}
		private CombineGroup(TweetCard origin) {
			this.originId = origin.getId();
			this.origin = origin;
		}
	}

}
