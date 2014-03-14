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
    public String privatePage() {
        Long uid = AuthUtil.checkLogin();
        return "forward:/private/"+uid;
    }

    @RequestMapping("/private/{id}")
    public String privatePage(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("id", id);
        UserCard thisUser = userService.getUserCard(AuthUtil.currentUid(), id);
        model.addAttribute("thisUserJson", JsonUtil.json(thisUser));
        model.remove("userSelfJson");
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
        model.addAttribute("blogs", blogReadService.getAllBlogDatas());
        return "blogs";
    }

    @RequestMapping("/write-blog")
    public String writeBlog() {
        AuthUtil.checkLogin();
        return "write-blog";
    }
    
    @RequestMapping("/blog/{blogId}/edit")
    public String blogEdit(@PathVariable("blogId") Long blogId, ModelMap model) {
        Long id = AuthUtil.checkLogin();
        
        BlogData blog = blogReadService.getBlogData(blogId);
        if (blog.getAuthorId().equals(id)) {
            model.addAttribute("blog", blog);
            return "write-blog";
        }
        else return "error";
    }
    
    @RequestMapping("/followings")
    public String followings() {
        Long uid = AuthUtil.checkLogin();
        return "forward:/followings/"+uid;
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
        Long uid = AuthUtil.checkLogin();
        return "forward:/followers/"+uid;
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

    @RequestMapping("/manip-tag")
    public void manipulateTag() {

    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
