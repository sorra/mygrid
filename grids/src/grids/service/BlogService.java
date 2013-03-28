package grids.service;

import grids.entity.Blog;
import grids.entity.Tweet;
import grids.repos.BlogRepos;
import grids.repos.TagRepos;
import grids.repos.UserRepos;

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
	
	@Transactional(readOnly=true)
	public Blog get(long blogId) {
		return blogRepos.get(blogId);
	}
	
	/**
	 * Experimental
	 * @return a sequential list of connected tweets
	 */
	@Transactional(readOnly=true)
	public List<Tweet> connectTweets(long blogId) {
		return blogRepos.connectTweets(blogId);
	}
	
	public long blog(long userId, String title, String content, long[] tagIds) {
		Blog blog = new Blog(title, content, userRepos.load(userId), new Date(), tagRepos.getTags(tagIds));
		blogRepos.save(blog);
		return blog.getId();
	}
	
	public boolean edit(long userId, long blogId, String title, String content, long[] tagIds) {
		Blog blog = blogRepos.load(blogId);
		if (blog.getAuthor().getId() == userId) {
			blog.setTitle(title);
			blog.setContent(content);
			blog.setTags(tagRepos.getTags(tagIds));
			return true;
		}
		else return false;
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
