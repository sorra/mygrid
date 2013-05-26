package grids.service;

import grids.entity.Blog;
import grids.entity.Tweet;
import grids.repository.CommentRepository;
import grids.repository.TweetRepository;
import grids.transfer.BlogData;
import grids.transfer.TweetCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
	@Autowired
	private UserService userService;
	@Autowired
	private TweetRepository tweetRepos;
	@Autowired
	private CommentRepository commentRepos;
	
	public BlogData getBlogData(Blog blog) {
		long authorId = blog.getAuthor().getId();
		return new BlogData(blog, userService.getUserCard(authorId));
	}

	public TweetCard getTweetCard(Tweet tweet) {
		return new TweetCard(tweet,
				forwardCount(tweet.getId()),
				commentCount(tweet.getId()));
	}

	public long forwardCount(long originId) {
		return tweetRepos.forwardCount(originId);
	}

	public long commentCount(long sourceId) {
		return commentRepos.commentCount(sourceId);
	}
}
