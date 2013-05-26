package grids.service;

import grids.entity.Blog;
import grids.entity.Comment;
import grids.entity.Tweet;
import grids.repository.CommentRepository;
import grids.repository.TagRepository;
import grids.repository.TweetRepository;
import grids.repository.UserRepository;
import grids.search.SearchBase;
import grids.transfer.TweetCard;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TweetPostService {
	@Autowired
	private SearchBase searchBase;
	@Autowired
	private TransferService transferService;
	@Autowired
	private UserRepository userRepos;
	@Autowired
	private TweetRepository tweetRepos;
	@Autowired
	private TagRepository tagRepos;
	@Autowired
	private CommentRepository commentRepos;
	
	public Tweet newTweet(long userId, String content, Collection<Long> tagIds) {
		Tweet tweet = new Tweet(content, userRepos.load(userId), new Date(),
				tagRepos.byIds(tagIds));
		tweetRepos.save(tweet);
		searchBase.index(tweet.getId(), transferService.getTweetCard(tweet));
		return tweet;
	}
	
	public void comment(long userId, String content, long sourceId) {
		Comment comment = new Comment(content, userRepos.load(userId),
				new Date(), tweetRepos.load(sourceId));
		commentRepos.save(comment);
	}
	
	public void share(long userId, String content, String sourceUrl) {
		//XXX
	}
	
	public void share(long userId, Blog blog) {
		final int SUM_LEN = 100;
		String content = blog.getContent();
		String summary = content.length()>SUM_LEN ? content.substring(0, SUM_LEN) : content;
		Tweet tweet = new Tweet(
				"发表了博客：["+blogRef(blog)+"] "+summary,
				userRepos.load(userId),
				new Date(),
				blog);
		tweetRepos.save(tweet);
		searchBase.index(tweet.getId(), transferService.getTweetCard(tweet));
	}
	
	public Tweet forward(long userId, String content, long originId) {
		Tweet tweet = new Tweet(content, userRepos.load(userId), new Date(),
				tweetRepos.load(originId));
		tweetRepos.save(tweet);
		searchBase.index(tweet.getId(), transferService.getTweetCard(tweet));
		return tweet;
	}
	
	public boolean delete(long userId, long tweetId) {
		Tweet tweet = tweetRepos.load(tweetId);
		if (userId == tweet.getAuthor().getId()) {
			tweetRepos.delete(tweet);
			searchBase.delete(TweetCard.class, tweetId);
			return true;
		}
		else return false;
	}
	
	private String blogRef(Blog blog) {
		return String.format("<a href=\"%s\">%s</a>",
				"/grids/blog/" + blog.getId(), blog.getTitle());
	}
}
