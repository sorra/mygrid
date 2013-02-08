package grids.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import grids.entity.Tag;

@Controller
@RequestMapping("/action")
public class Action {
	@RequestMapping(value="/login")
	public String login(@RequestParam("username") String username,
						@RequestParam("password") String password) {
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		boolean valid = true;
		if (valid){
			return "redirect:/";
		} else {
			return "redirect:/login";
		}
	}
	@RequestMapping("/post")
	public boolean post(String content, List<Tag> tags) {
		return true;
	}
	@RequestMapping("/istream")
	@ResponseBody
	public String istream() {
		return "This is istream.";
	}
}
