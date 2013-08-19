package sage.domain.service;

import httl.util.StringUtils;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sage.domain.repository.CommentRepository;
import sage.domain.repository.TagRepository;
import sage.domain.repository.TweetRepository;
import sage.domain.repository.UserRepository;
import sage.domain.search.SearchBase;
import sage.entity.Blog;
import sage.entity.Comment;
import sage.entity.Tweet;
import sage.transfer.TweetCard;

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
        content = StringUtils.escapeXml(content);
		Tweet tweet = new Tweet(content, userRepos.load(userId), new Date(),
				tagRepos.byIds(tagIds));
		tweetRepos.save(tweet);
		searchBase.index(tweet.getId(), transferService.getTweetCard(tweet));
		return tweet;
	}
	
	public void comment(long userId, String content, long sourceId) {
        content = StringUtils.escapeXml(content);
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
        content = StringUtils.escapeXml(content);
	    Tweet origin = tweetRepos.load(originId);
		Tweet tweet = new Tweet(content,
		        userRepos.load(userId), new Date(), pureOrigin(origin));
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
	
	private Tweet pureOrigin(Tweet tweet) {
	    if (tweet.getOrigin() == null) {
	        return tweet;
	    }
	    else {
	        return pureOrigin(tweet.getOrigin());
        }
	}
}
