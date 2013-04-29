package grids.service;

import grids.entity.Blog;
import grids.entity.Tweet;
import grids.repos.BlogRepos;
import grids.repos.FollowRepos;
import grids.repos.TagRepos;
import grids.repos.UserRepos;
import grids.transfer.BlogData;
import grids.transfer.UserCard;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogService {
	@Autowired
	private BlogRepos blogRepos;
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private TagRepos tagRepos;
	@Autowired
	private FollowRepos followRepos;
	@Autowired
	private TweetService tweetService;
	
	@Transactional(readOnly=true)
	public BlogData getBlogData(long blogId) {
		Blog blog = blogRepos.get(blogId);
		long authorId = blog.getAuthor().getId();
		return new BlogData(blog,
				new UserCard(blog.getAuthor(),
						followRepos.followingCount(authorId),
						followRepos.followerCount(authorId))
		);
	}
	
	/**
	 * Experimental
	 * @return a sequential list of connected tweets
	 */
	@Transactional(readOnly=true)
	public List<Tweet> connectTweets(long blogId) {
		return blogRepos.connectTweets(blogId);
	}
	
	public Blog newBlog(long userId, String title, String content, long[] tagIds, boolean share) {
		Blog blog = new Blog(title, content, userRepos.load(userId), new Date(), tagRepos.getTags(tagIds));
		blogRepos.save(blog);
		if(share) {tweetService.share(userId, blog);}
		return blog;
	}
	
	public Blog edit(long userId, long blogId, String title, String content, long[] tagIds) {
		Blog blog = blogRepos.get(blogId);
		if (blog.getAuthor().getId() == userId) {
			blog.setTitle(title);
			blog.setContent(content);
			blog.setTags(tagRepos.getTags(tagIds));
			return blog;
		}
		else return null;
	}
	public boolean delete(long userId, long blogId) {
		Blog blog = blogRepos.load(blogId);
		if (blog.getAuthor().getId() == userId) {
			blogRepos.delete(blog);
			return true;
		}
		else return false;
	}
}
