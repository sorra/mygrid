package sage.web.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sage.domain.service.BlogReadService;
import sage.domain.service.TweetReadService;
import sage.domain.service.UserService;
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
    private BlogReadService blogReadService;
    @Autowired
    private TweetReadService tweetReadService;

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
        AuthUtil.checkCurrentUid();
        return "write-blog";
    }
    
    @RequestMapping("/blog/{blogId}/edit")
    public String blogEdit(@PathVariable("blogId") Long blogId, ModelMap model) {
    	Long currentUid = AuthUtil.checkCurrentUid();
    	
        BlogData blog = blogReadService.getBlogData(blogId);
        if (blog.getAuthorId().equals(currentUid)) {
            model.addAttribute("blog", blog);
            return "write-blog";
        }
        else return "error";
    }
    
    @RequestMapping("/manip-tag")
    public void manipulateTag() {

    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
