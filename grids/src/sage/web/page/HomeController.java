package sage.web.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sage.domain.service.RelationService;
import sage.domain.service.TagService;
import sage.domain.service.UserService;
import sage.web.auth.AuthUtil;
import sage.web.context.JsonUtil;

@Controller
@RequestMapping
public class HomeController {
  @Autowired
  UserService userService;
  @Autowired
  TagService tagService;
  @Autowired
  RelationService relationService;

  @RequestMapping({ "/", "/home" })
  public String home(ModelMap model) {
    Long uid = AuthUtil.checkCurrentUid();

    String friendsJson = JsonUtil.json(relationService.friends(uid));
    model.addAttribute("friendsJson", friendsJson);
    return "home";
  }

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @RequestMapping("/register")
  public String register() {
    return "register";
  }
}
