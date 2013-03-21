package grids.web;

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
	
	@RequestMapping("/public/{id}")
	public ModelAndView publicPage(@PathVariable int id) {
		return null;
	}
	
	@RequestMapping("/private/{id}")
	public ModelAndView privatePage(@PathVariable int id) {
		return null;
	}
	
	@RequestMapping("/group/{id}")
	public ModelAndView groupPage(@PathVariable int id) {
		return null;
	}
}
