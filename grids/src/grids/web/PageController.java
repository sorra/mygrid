package grids.web;

import grids.service.BlogReadService;
import grids.service.UserService;
import grids.transfer.BlogData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PageController {
	private final static Logger logger = LoggerFactory.getLogger(PageController.class);
	@Autowired
	UserService userService;
	@Autowired
	BlogReadService blogReadService;
	
	@RequestMapping("/public/{id}")
	public String publicPage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/private/{id}")
	public String privatePage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/group/{id}")
	public String groupPage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/blog/{id}")
	public String blogPage(@PathVariable long id, ModelMap model) {
		BlogData blog = blogReadService.getBlogData(id);
		if (blog == null) {
			logger.info("blog {} is null!", id);
			return "redirect:/";
		}
		
		model.addAttribute("blog", blog)
			.addAttribute("tweets", blogReadService.connectTweets(id));
		return "blog.httl";
	}
	
	@RequestMapping("/blogs")
	public String blogs(ModelMap model) {
		model.addAttribute("blogs", blogReadService.getAllBlogData());
		return "blogs.httl";
	}
	
	@RequestMapping("/writeBlog")
	public String writeBlog() {
		return "write-blog.httl";
	}
	
	@RequestMapping("/test")
	public String test() {
		return "test.httl";
	}
}
