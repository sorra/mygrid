package grids.service;

import grids.entity.Comment;
import grids.entity.Tweet;
import grids.repos.CommentRepos;
import grids.repos.TagRepos;
import grids.repos.TweetRepos;
import grids.repos.UserRepos;

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
	public Tweet get(long tweetId) {
		return tweetRepos.get(tweetId);
	}
	
	public void tweet(long userId, String content, long[] tagIds) {
		Tweet tweet = new Tweet(content, userRepos.load(userId), new Date(),
				tagRepos.getTags(tagIds));
		tweetRepos.save(tweet);
	}
	
	public void comment(long userId, String content, long sourceId) {
		Comment comment = new Comment(content, userRepos.load(userId),
				new Date(), tweetRepos.load(sourceId));
		commentRepos.save(comment);
	}
	
	public Collection<Comment> getComments(long sourceId) {
		return tweetRepos.load(sourceId).getComments();
	}
	
	public void share(long userId, String content, String sourceUrl) {
		//XXX
	}
	
	public void forward(long userId, String content, long originId) {
		Tweet tweet = new Tweet(content, userRepos.load(userId), new Date(),
				tweetRepos.load(originId));
		tweetRepos.save(tweet);
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
