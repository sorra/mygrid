package sage.web.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sage.domain.service.UserService;
import sage.transfer.UserCard;
import sage.web.auth.AuthUtil;
import sage.web.context.JsonUtil;

@Controller
public class StreamPageController {
    @Autowired
    private UserService userService;
    
    @RequestMapping("/public/{id}")
    public String publicPage(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("id", id);
        return "public-page";
    }
    
    @RequestMapping("/private")
    public String privatePage() {
        return "forward:/private/" + AuthUtil.checkCurrentUid();
    }

    @RequestMapping("/private/{id}")
    public String privatePage(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("id", id);
        UserCard thisUser = userService.getUserCard(AuthUtil.checkCurrentUid(), id);
        model.addAttribute("thisUserJson", JsonUtil.json(thisUser));
        model.remove("userSelfJson");
        return "private-page";
    }

    @RequestMapping("/group/{id}")
    public String groupPage(@PathVariable("id") long id) {
        return "group-page";
    }
}
