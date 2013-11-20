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
    private BlogRepository blogRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TagRepository tagRepo;
    
    public Blog newBlog(long userId, String title, String content, Collection<Long> tagIds) {
        Blog blog = new Blog(title, content, userRepo.load(userId), new Date(), tagRepo.byIds(tagIds));
        escapeAndSet(blog);
        
        blogRepo.save(blog);
        searchBase.index(blog.getId(), transferService.getBlogData(blog));
        return blog;
    }
    
    public Blog edit(long userId, long blogId, String title, String content, Collection<Long> tagIds) {
        Blog blog = blogRepo.get(blogId);
        if (blog.getAuthor().getId() == userId) {
            blog.setTime(new Date());
            blog.setTitle(title);
            blog.setContent(content);
            escapeAndSet(blog);
            blog.setTags(tagRepo.byIds(tagIds));
            
            blogRepo.update(blog);
            searchBase.index(blog.getId(), transferService.getBlogData(blog));
            return blog;
        }
        else return null;
    }
    
    public boolean delete(long userId, long blogId) {
        Blog blog = blogRepo.load(blogId);
        if (blog.getAuthor().getId() == userId) {
            blogRepo.delete(blog);
            searchBase.delete(BlogData.class, blog.getId());
            return true;
        }
        else return false;
    }
    
    private void escapeAndSet(Blog blog) {
        String title = StringUtils.escapeXml(blog.getTitle());
        String content = StringUtils.escapeXml(blog.getContent()).replace("\n", "  \n");
        blog.setTitle(title);
        blog.setContent(content);
    }
}
