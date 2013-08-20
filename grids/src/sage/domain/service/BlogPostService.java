package sage.domain.service;

import httl.util.StringUtils;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sage.domain.repository.BlogRepository;
import sage.domain.repository.TagRepository;
import sage.domain.repository.UserRepository;
import sage.domain.search.SearchBase;
import sage.entity.Blog;
import sage.transfer.BlogData;

@Service
@Transactional
public class BlogPostService {
	@Autowired
	private SearchBase searchBase;
	@Autowired
	private TransferService transferService;
	@Autowired
	private BlogRepository blogRepos;
	@Autowired
	private UserRepository userRepos;
	@Autowired
	private TagRepository tagRepos;
	
	public Blog newBlog(long userId, String title, String content, Collection<Long> tagIds) {
        title = StringUtils.escapeXml(title);
	    content = StringUtils.escapeXml(content);
		Blog blog = new Blog(title, content, userRepos.load(userId), new Date(), tagRepos.byIds(tagIds));
		blogRepos.save(blog);
		searchBase.index(blog.getId(), transferService.getBlogData(blog));
		return blog;
	}
	
	public Blog edit(long userId, long blogId, String title, String content, Collection<Long> tagIds) {
        title = StringUtils.escapeXml(title);
	    content = StringUtils.escapeXml(content);
        content = content.replace("\n", "<br/>");
	    Blog blog = blogRepos.get(blogId);
		if (blog.getAuthor().getId() == userId) {
			blog.setTitle(title);
			blog.setContent(content);
			blog.setTags(tagRepos.byIds(tagIds));
			searchBase.index(blog.getId(), transferService.getBlogData(blog));
			return blog;
		}
		else return null;
	}
	public boolean delete(long userId, long blogId) {
		Blog blog = blogRepos.load(blogId);
		if (blog.getAuthor().getId() == userId) {
			blogRepos.delete(blog);
			searchBase.delete(BlogData.class, blog.getId());
			return true;
		}
		else return false;
	}
}
