package grids.service;

import grids.entity.Blog;
import grids.entity.Tweet;
import grids.repos.BlogRepos;
import grids.transfer.BlogData;
import grids.transfer.TweetCard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class BlogReadService {
	@Autowired
	private BlogRepos blogRepos;
	@Autowired
	private UserService userService;
	@Autowired
	private TweetReadService tweetReadService;

	/**
	 * @return blogData | null
	 */
	public BlogData getBlogData(long blogId) {
		Blog blog = blogRepos.get(blogId);
		if (blog == null) {return null;}
		
		long authorId = blog.getAuthor().getId();
		return new BlogData(blog, userService.getUserCard(authorId));
	}

	/**
	 * Experimental
	 * @return a sequential list of connected tweets
	 */
	public List<TweetCard> connectTweets(long blogId) {
		List<TweetCard> tcs = new ArrayList<>();
		for (Tweet tweet : blogRepos.connectTweets(blogId)) {
			tcs.add(tweetReadService.getTweetCard(tweet));
		}
		return tcs;
	}

}
