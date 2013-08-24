package sage.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import sage.domain.service.TagService;
import sage.domain.service.UserService;
import sage.web.auth.AuthUtil;

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
    public String home(HttpSession session, ModelMap model) throws JsonProcessingException {
        Long uid = AuthUtil.checkLoginUid(session);
        // Temporal
        if (uid == null) {
            return "redirect:/login";
        }
        //
        String userSelfJson = objectMapper.writeValueAsString(userService.getSelf(uid));
        model.addAttribute("userSelfJson", userSelfJson);
        String tagTreeJson = objectMapper.writeValueAsString(tagService.getTagTree());
        model.addAttribute("tagTreeJson", tagTreeJson);
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
