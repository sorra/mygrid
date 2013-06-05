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
		Long uid = AuthUtil.checkLoginUid(session);
		return userService.getSelf(uid);
	}
	
	@RequestMapping("/card/{id}")
	@ResponseBody
	public UserCard userCard(HttpSession session, @PathVariable("id") long id) {
		Long uid = AuthUtil.checkLoginUid(session);
		return userService.getUserCard(uid, id);
	}
	
	@RequestMapping("/info/{id}")
	public ModelAndView userInfo(@PathVariable("id") long id) {
		return null;
	}
}
