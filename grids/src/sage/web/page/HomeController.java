package sage.web.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sage.domain.service.TagService;
import sage.domain.service.UserService;
import sage.web.auth.AuthUtil;
import sage.web.context.JsonUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping({ "/", "/home" })
    public String home(ModelMap model) {
        Long uid = AuthUtil.checkLogin();
        // Temporal
        if (uid == null) {
            return "redirect:/login";
        }
        //
        String userSelfJson = JsonUtil.json(userService.getSelf(uid));
        model.addAttribute("userSelfJson", userSelfJson);
        String tagTreeJson = JsonUtil.json(tagService.getTagTree());
        model.addAttribute("tagTreeJson", tagTreeJson);
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
