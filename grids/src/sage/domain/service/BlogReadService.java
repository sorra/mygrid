package sage.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sage.domain.Edge;
import sage.domain.repository.BlogRepository;
import sage.entity.Blog;
import sage.transfer.BlogData;

@Service
@Transactional(readOnly=true)
public class BlogReadService {
    @Autowired
    private BlogRepository blogRepo;

    /**
     * @return blogData | null
     */
    public BlogData getBlogData(long blogId) {
        Blog blog = blogRepo.get(blogId);
        if (blog == null) {
            return null;
        }
        return new BlogData(blog);
    }


    public List<BlogData> getAllBlogData() {
        List<BlogData> allBD = new ArrayList<>();
        for (Blog blog : blogRepo.all()) {
            allBD.add(new BlogData(blog));
        }
        return allBD;
    }
    
    public List<BlogData> blogStream(long userId, Edge edge) {
    	List<BlogData> blogs = new ArrayList<>();
    	//TODO
    	
    	return blogs;
    }
}
