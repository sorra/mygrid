package grids.service;

import grids.entity.Blog;
import grids.repository.BlogRepository;
import grids.repository.TagRepository;
import grids.repository.UserRepository;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogPostService {
	@Autowired
	private BlogRepository blogRepos;
	@Autowired
	private UserRepository userRepos;
	@Autowired
	private TagRepository tagRepos;
	@Autowired
	private TweetPostService tweetService;
	
	public Blog newBlog(long userId, String title, String content, Collection<Long> tagIds, boolean share) {
		Blog blog = new Blog(title, content, userRepos.load(userId), new Date(), tagRepos.getTags(tagIds));
		blogRepos.save(blog);
		if(share) {tweetService.share(userId, blog);}
		return blog;
	}
	
	public Blog edit(long userId, long blogId, String title, String content, Collection<Long> tagIds) {
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
