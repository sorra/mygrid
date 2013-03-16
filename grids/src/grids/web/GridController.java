package grids.web;

import grids.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class GridController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/publicGrid/{id}")
	public ModelAndView publicGrid(@PathVariable int id) {
		return null;
	}
	
	@RequestMapping("/privateGrid/{id}")
	public ModelAndView privateGrid(@PathVariable int id) {
		return null;
	}
}
