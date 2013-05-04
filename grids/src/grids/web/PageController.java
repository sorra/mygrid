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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class PageController {
	private final static Logger logger = LoggerFactory.getLogger(PageController.class);
	@Autowired
	UserService userService;
	@Autowired
	BlogReadService blogReadService;
	
	@RequestMapping("/public/{id}")
	public ModelAndView publicPage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/private/{id}")
	public ModelAndView privatePage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/group/{id}")
	public ModelAndView groupPage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/blog/{id}")
	public String blogPage(@PathVariable long id, ModelMap model) {
		BlogData blog = blogReadService.getBlogData(id);
		if (blog == null) {
			logger.info("blog {} is null", id);
		}
		
		model.addAttribute("blog", blog)
			.addAttribute("tweets", blogReadService.connectTweets(id));
		return "blog.httl";
	}
	
	@RequestMapping("/blogs")
	public String blogs() {
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
