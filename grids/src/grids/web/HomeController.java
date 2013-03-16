package grids.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
	@RequestMapping({"/", "/home"})
	public String home() {
		return "home.httl";
	}
	@RequestMapping("/login")
	public String login() {
		return "login.httl";
	}
}
