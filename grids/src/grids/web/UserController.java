package grids.web;

import javax.servlet.http.HttpSession;

import grids.service.UserService;
import grids.transfer.UserSelf;
import grids.transfer.UserCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping("/self")
	@ResponseBody
	public UserSelf self(HttpSession session) {
		long uid = AuthUtil.checkLoginUid(session);
		return userService.getSelfInfo(uid);
	}
	
	@RequestMapping("/card/{id}")
	@ResponseBody
	public UserCard userCard(@PathVariable long id) {
		return userService.getUserCard(id);
	}
	
	@RequestMapping("/info/{id}")
	public ModelAndView userInfo(@PathVariable long id) {
		return null;
	}
}
