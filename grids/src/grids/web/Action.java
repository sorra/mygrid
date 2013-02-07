package grids.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import grids.entity.Tag;

@Controller
@RequestMapping
public class Action {
	@RequestMapping("/login")
	public String login(String username, String password) {
		return "redirect:";
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
