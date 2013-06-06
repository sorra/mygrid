package grids.web;

import grids.entity.User;
import grids.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private final static Logger logger = LoggerFactory.getLogger(AuthController.class);
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request,
						@RequestParam("email") String email,
						@RequestParam("password") String password) {
		logger.info("email: {} password: {}", email, password);
		User user = userService.login(email, password);
		if (user != null){
			HttpSession sesison = request.getSession(true);
			sesison.setAttribute(SessionKeys.UID, user.getId());
			logger.info("User {} logged in.", user.getId());
			return "redirect:/";
		} else {
			logger.info("{} login failed.", email);
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
		logger.info("register TODO");
		return userService.register(null)>=0 ? SUCCESS : FAILURE;
	}
}
