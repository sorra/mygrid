package grids.web;

import grids.service.BlogService;
import grids.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class PageController {
	@Autowired
	UserService userService;
	@Autowired
	BlogService blogService;
	
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
	public ModelAndView blogPage(@PathVariable long id) {
		ModelAndView mav = new ModelAndView("blog.httl");
		mav.getModelMap().addAttribute("blog", blogService.getBlogData(id));
		return mav;
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
