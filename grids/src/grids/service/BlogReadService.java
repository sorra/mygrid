package grids.service;

import grids.entity.Blog;
import grids.repository.BlogRepository;
import grids.repository.TweetRepository;
import grids.transfer.BlogData;

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
	private TransferService transferService;

	/**
	 * @return blogData | null
	 */
	public BlogData getBlogData(long blogId) {
		Blog blog = blogRepos.get(blogId);
		if (blog == null) {
			return null;
		}
		return transferService.getBlogData(blog);
	}


	public List<BlogData> getAllBlogData() {
		List<BlogData> allBD = new ArrayList<>();
		for (Blog blog : blogRepos.all()) {
			allBD.add(transferService.getBlogData(blog));
		}
		return allBD;
	}
}
