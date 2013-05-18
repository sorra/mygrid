package grids.service;

import grids.entity.Blog;
import grids.entity.Tweet;
import grids.repository.BlogRepository;
import grids.repository.TweetRepository;
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
	private BlogRepository blogRepos;
	@Autowired
	private TweetRepository tweetRepos;
	@Autowired
	private UserService userService;
	@Autowired
	private TweetReadService tweetReadService;

	/**
	 * @return blogData | null
	 */
	public BlogData getBlogData(long blogId) {
		Blog blog = blogRepos.get(blogId);
		if (blog == null) {
			return null;
		}
		return getBlogData(blog);
	}
	
	public BlogData getBlogData(Blog blog) {
		long authorId = blog.getAuthor().getId();
		return new BlogData(blog, userService.getUserCard(authorId));
	}

	/**
	 * Experimental
	 * @return a sequential list of connected tweets
	 */
	public List<TweetCard> connectTweets(long blogId) {
		List<TweetCard> tcs = new ArrayList<>();
		for (Tweet tweet : tweetRepos.connectTweets(blogId)) {
			tcs.add(tweetReadService.getTweetCard(tweet));
		}
		return tcs;
	}

	public List<BlogData> getAllBlogData() {
		List<BlogData> allBD = new ArrayList<>();
		for (Blog blog : blogRepos.all()) {
			allBD.add(getBlogData(blog));
		}
		return allBD;
	}
}
