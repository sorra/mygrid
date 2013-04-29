package grids.service;

import grids.entity.Blog;
import grids.entity.Comment;
import grids.entity.Tweet;
import grids.repos.CommentRepos;
import grids.repos.TagRepos;
import grids.repos.TweetRepos;
import grids.repos.UserRepos;
import grids.transfer.TweetCard;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TweetService {
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private TweetRepos tweetRepos;
	@Autowired
	private TagRepos tagRepos;
	@Autowired
	private CommentRepos commentRepos;
	
	@Transactional(readOnly=true)
	public TweetCard getTweetCard(long tweetId) {
		return new TweetCard(tweetRepos.get(tweetId),
				getForwardCount(tweetId),
				getCommentCount(tweetId));
	}
	
	public Tweet newTweet(long userId, String content, long[] tagIds) {
		Tweet tweet = new Tweet(content, userRepos.load(userId), new Date(),
				tagRepos.getTags(tagIds));
		tweetRepos.save(tweet);
		return tweet;
	}
	
	public void comment(long userId, String content, long sourceId) {
		Comment comment = new Comment(content, userRepos.load(userId),
				new Date(), tweetRepos.load(sourceId));
		commentRepos.save(comment);
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
	
	public void share(long userId, String content, String sourceUrl) {
		//XXX
	}
	
	public void share(long userId, Blog blog) {
		final int SUM_LEN = 100;
		String content = blog.getContent();
		String summary = content.length()>SUM_LEN ? content.substring(0, SUM_LEN) : content;
		Tweet tweet = new Tweet(
				"发表了博客：["+blog.getTitle()+"] "+summary,
				userRepos.load(userId),
				new Date(),
				blog.getTags());
		tweetRepos.save(tweet);
	}
	
	public Tweet forward(long userId, String content, long originId) {
		Tweet tweet = new Tweet(content, userRepos.load(userId), new Date(),
				tweetRepos.load(originId));
		tweetRepos.save(tweet);
		return tweet;
	}
	
	public boolean delete(long userId, long tweetId) {
		Tweet tweet = tweetRepos.load(tweetId);
		if (userId == tweet.getAuthor().getId()) {
			tweetRepos.delete(tweet);
			return true;
		}
		else return false;
	}
}
