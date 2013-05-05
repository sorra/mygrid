package grids.service;

import grids.entity.Comment;
import grids.entity.Follow;
import grids.entity.Tweet;
import grids.repos.FollowRepos;
import grids.repos.TweetRepos;
import grids.transfer.TweetCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class TweetReadService {
	@Autowired
	private TweetRepos tweetRepos;
	@Autowired
	private FollowRepos followRepos;
	
	public List<TweetCard> getIstreamTweetCards(long userId) {
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
			tcs.add(getTweetCard(tweet));
		}
		return tcs;
	}

	public TweetCard getTweetCard(long tweetId) {
		return getTweetCard(tweetRepos.get(tweetId));
	}

	public TweetCard getTweetCard(Tweet tweet) {
		return new TweetCard(tweet,
				getForwardCount(tweet.getId()),
				getCommentCount(tweet.getId()));
	}

	public Collection<Tweet> getForwards(long originId) {
		return tweetRepos.findByOrigin(originId);
	}

	public Collection<Comment> getComments(long sourceId) {
		return tweetRepos.load(sourceId).getComments();
	}

	public int getForwardCount(long originId) {
		return getForwards(originId).size();
	}

	public int getCommentCount(long sourceId) {
		return getComments(sourceId).size();
	}

}
