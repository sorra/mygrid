package grids.web;

import javax.servlet.http.HttpSession;

import grids.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping
public class HomeController {
	@Autowired
	UserService userService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping({"/", "/home"})
	public String home(HttpSession session, ModelMap model) throws JsonProcessingException {
		Long uid = AuthUtil.checkLoginUid(session);
		// Temporal
		if (uid == null) {uid = 1L;}
		//
		String selfJson = objectMapper.writeValueAsString(userService.getSelf(uid));
		model.addAttribute("selfJson", selfJson);
		return "home.httl";
	}
	@RequestMapping("/login")
	public String login() {
		return "login.httl";
	}
}
