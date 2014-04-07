package sage.web.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sage.domain.service.RelationService;
import sage.domain.service.UserService;
import sage.entity.Follow;
import sage.transfer.UserCard;
import sage.web.auth.AuthUtil;
import sage.web.context.JsonUtil;

@Controller
public class RelationPageController {
  @Autowired
  private UserService userService;
  @Autowired
  private RelationService relationService;

  @RequestMapping("/followings")
  public String followings() {
    return "forward:/followings/" + AuthUtil.checkCurrentUid();
  }

  @RequestMapping("/followings/{uid}")
  public String followings(@PathVariable("uid") long uid, ModelMap model) {
    List<String> followingsInJson = new ArrayList<>();
    for (Follow follow : relationService.followings(uid)) {
      UserCard followingUc = userService.getUserCard(uid, follow.getTarget().getId());
      followingsInJson.add(JsonUtil.json(followingUc));
    }
    model.addAttribute("followings", followingsInJson);
    return "followings";
  }

  @RequestMapping("/followers")
  public String followers() {
    return "forward:/followers/" + AuthUtil.checkCurrentUid();
  }

  @RequestMapping("/followers/{uid}")
  public String followers(@PathVariable("uid") long uid, ModelMap model) {
    List<String> followersInJson = new ArrayList<>();
    for (Follow follow : relationService.followers(uid)) {
      UserCard followerUc = userService.getUserCard(uid, follow.getSource().getId());
      followersInJson.add(JsonUtil.json(followerUc));
    }
    model.addAttribute("followers", followersInJson);
    return "followers";
  }
}
