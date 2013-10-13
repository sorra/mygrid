package sage.web.page;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sage.domain.service.BlogReadService;
import sage.domain.service.RelationService;
import sage.domain.service.TagService;
import sage.domain.service.TransferService;
import sage.domain.service.TweetReadService;
import sage.domain.service.UserService;
import sage.entity.Follow;
import sage.transfer.BlogData;
import sage.transfer.TweetCard;
import sage.transfer.UserCard;
import sage.web.auth.AuthUtil;
import sage.web.context.JsonUtil;

@Controller
@RequestMapping
public class PageController {
    private final static Logger logger = LoggerFactory.getLogger(PageController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogReadService blogReadService;
    @Autowired
    private TweetReadService tweetReadService;
    @Autowired
    private TransferService transferService;

    @RequestMapping("/public/{id}")
    public String publicPage(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("id", id);
        return "public-page";
    }
    
    @RequestMapping("/private")
    public String privatePage(ModelMap model) {
        Long id = AuthUtil.checkLogin();
        return privatePage(id, model);
    }

    @RequestMapping("/private/{id}")
    public String privatePage(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("id", id);
        return "private-page";
    }

    @RequestMapping("/group/{id}")
    public String groupPage(@PathVariable("id") long id) {
        return "group-page";
    }
    
    @RequestMapping("/tweet/{id}")
    public String tweetPage(@PathVariable("id") long id, ModelMap model) {
        TweetCard tc = tweetReadService.getTweetCard(id);
        String tcJson = JsonUtil.json(tc);
        model.addAttribute("tcJson", tcJson);
        return "tweet";
    }

    @RequestMapping("/blog/{id}")
    public String blogPage(@PathVariable("id") long id, ModelMap model) {
        BlogData blog = blogReadService.getBlogData(id);
        if (blog == null) {
            logger.info("blog {} is null!", id);
            return "redirect:/";
        }

        model.addAttribute("blog", blog).addAttribute("tweets", tweetReadService.connectTweets(id));
        return "blog";
    }

    @RequestMapping("/blogs")
    public String blogs(ModelMap model) {
        model.addAttribute("blogs", blogReadService.getAllBlogData());
        return "blogs";
    }

    @RequestMapping("/write-blog")
    public String writeBlog(ModelMap model) {
        Long uid = AuthUtil.checkLogin();

        String selfJson = JsonUtil.json(userService.getSelf(uid));
        model.addAttribute("selfJson", selfJson);
        String tagTreeJson = JsonUtil.json(tagService.getTagTree());
        model.addAttribute("tagTreeJson", tagTreeJson);
        return "write-blog";
    }
    
    @RequestMapping("/followings")
    public String followings(ModelMap model) {
        Long uid = AuthUtil.checkLogin();
        return followings(uid, model);
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
    public String followers(ModelMap model) {
        Long uid = AuthUtil.checkLogin();
        return followers(uid, model);
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

    @RequestMapping("/manipTag")
    public void manipulateTag() {

    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
