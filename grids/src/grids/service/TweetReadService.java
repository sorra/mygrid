package grids.service;

import grids.entity.Comment;
import grids.entity.Tweet;
import grids.repos.TweetRepos;
import grids.transfer.TweetCard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class TweetReadService {
	@Autowired
	private TweetRepos tweetRepos;

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
