package grids.web;

import grids.entity.User;
import grids.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private static final String SUCCESS = "成功";
	private static final String FAILURE = "失败";
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request,
						@RequestParam("username") String username,
						@RequestParam("password") String password) {
		System.out.println("username: " + username + " password: " + password);
		User user = userService.login(username, password);
		if (user != null){
			HttpSession sesison = request.getSession(true);
			sesison.setAttribute(SessionKeys.UID, user.getId());
			return "redirect:/";
		} else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public String register() {
		return userService.register(null)>=0 ? SUCCESS : FAILURE;
	}
}
