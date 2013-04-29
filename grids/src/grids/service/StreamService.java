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
public class StreamService {
	@Autowired
	private FollowRepos followRepos;
	@Autowired
	private TagRepos tagRepos;
	@Autowired
	private TweetRepos tweetRepos;
	@Autowired
	private CommentRepos commentRepos;
	@Autowired
	private BlogRepos blogRepos;
	
	/*
	 * TODO Deliminate transaction scope
	 */
	@Transactional(readOnly=true)
	public Stream istream(long userId) {
		
		//XXX consider fetch_size limit
		List<Tweet> tweets = new ArrayList<>();
		tweets.addAll(tweetRepos.tweetsByAuthor(userId));
		List<Follow> followings = followRepos.followings(userId);
		for (Follow follow : followings) {
			tweets.addAll(tweetRepos.tweetsByAuthor(
					follow.getTarget().getId(), follow.getTags()));
		}
		
		List<TweetCard> tcs = new ArrayList<>();
		for (Tweet tweet : tweets) {
			// How to optimize the counting, by session cache?
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
		//TODO Pull-near
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

	@Transactional(readOnly=true)
	public Stream tagStream(long tagId) {
//		List<Tweet> tweets = tweetRepos.tweets(tagRepos.get(tagId).descendants());
		
		Stream stream = new Stream(0);
//		for (Tweet tweet : tweets) {
//			stream.add(new TweetCard(tweet, 42, 42));
//		}
		return stream;
	}

	@Transactional(readOnly=true)
	public Stream personalStream(long userId) {
		return null;
	}
}
