package grids.service;

import grids.domain.TweetOnIdComparator;
import grids.entity.Follow;
import grids.entity.Tweet;
import grids.transfer.CombineGroup;
import grids.transfer.Item;
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
	@Autowired
	private CommentRepos commentRepos;
	
	public Stream istream(long userId) {
		
		//XXX consider fetch_size limit
		List<Tweet> tweets = new ArrayList<>();
		tweets.addAll(tweetRepos.tweets(userId));
		List<Follow> followings = followRepos.followings(userId);
		for (Follow follow : followings) {
			tweets.addAll(tweetRepos.tweets(
					follow.getTarget().getId(), follow.getTags()));
		}
		
		List<TweetCard> tcs = new ArrayList<>();
		for (Tweet tweet : tweets) {
			tcs.add(new TweetCard(tweet,
					tweetRepos.forwardCount(tweet.getId()),
					commentRepos.commentCount(tweet.getId())
					));
		}
		
		Stream stream = new Stream(userId);
		stream.addAll(higherSort(tcs));
		return stream;
	}
	
	private List<Item> higherSort(List<TweetCard> tcs) {
		Collections.sort(tcs, new TweetOnIdComparator());
		//TODO Combine & Pull-near
		return combine(tcs);
	}

	private List<Item> combine(List<TweetCard> tcs) {
		List<CombineGroup> groupSeq = new ArrayList<>();
		for (TweetCard tc : tcs) {
			if (tc.getOrigin() != null) {
				long originId = tc.getOrigin().getId();
				CombineGroup foundGroup = findInSeq(originId, groupSeq);
				if (foundGroup != null) {
					foundGroup.getForwards().add(tc);
				}
				else {
					groupSeq.add(CombineGroup.newByFirst(tc));
				}
			}
			else {
				CombineGroup foundGroup = findInSeq(tc.getId(), groupSeq);
				if (foundGroup != null) {
					foundGroup.addOrigin(tc);
				}
				else {
					groupSeq.add(CombineGroup.newByOrigin(tc));
				}
			}
		}

		List<Item> sequence = new ArrayList<>(groupSeq.size());
		for (CombineGroup group : groupSeq) {
			TweetCard singleMember = group.singleMember();
			if (singleMember != null) {
				sequence.add(singleMember);
			}
			else sequence.add(group);
		}
		return sequence;
	}
	
	private CombineGroup findInSeq(long id, List<CombineGroup> groupSequence) {
		for (CombineGroup group : groupSequence) {
			if (group.getOrigin().getId() == id) {
				return group;
			}
		}
		return null;
	}

}
