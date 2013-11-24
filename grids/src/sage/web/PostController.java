 package sage.web;

import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sage.domain.service.BlogPostService;
import sage.domain.service.TweetPostService;
import sage.entity.Blog;
import sage.entity.Tweet;
import sage.web.auth.AuthUtil;

@Controller
@RequestMapping(value="/post", method=RequestMethod.POST)
public class PostController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TweetPostService tweetPostService;
    @Autowired
    private BlogPostService blogService;
    
    @RequestMapping("/tweet")
    @ResponseBody
    public boolean tweet(
            @RequestParam("content") String content, 
            @RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
        Long uid = AuthUtil.checkLogin();
        if (content.isEmpty()) {return false;}
        if (content.length() > 2000) {return false;}
        if (tagIds == null) {tagIds = Collections.EMPTY_LIST;}
        
        Tweet tweet = tweetPostService.newTweet(uid, content, tagIds);
        logger.info("post tweet {} success", tweet.getId());
        return true;
    }
    
    @RequestMapping("/forward")
    @ResponseBody
    public boolean forward(
            @RequestParam("content") String content,
            @RequestParam("originId") Long originId) {
        Long uid = AuthUtil.checkLogin();

        Tweet tweet = tweetPostService.forward(uid, content, originId);
        logger.info("forward tweet {} success", tweet.getId());
        return true;
    }
    
    @RequestMapping("/blog")
    @ResponseBody
    public boolean blog(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
        Long uid = AuthUtil.checkLogin();
        if (title.isEmpty() || content.isEmpty()) {return false;}
        if (tagIds == null) {tagIds = Collections.EMPTY_LIST;}

        Blog blog = blogService.newBlog(uid, title, content, tagIds);
        tweetPostService.share(uid, blog);
        if (blog != null) {
            logger.info("post blog {} success", blog.getId());
        }
        return blog != null;
    }
    
    @RequestMapping("/edit-blog/{blogId}")
    @ResponseBody
    public boolean editBlog(
            @PathVariable("blogId") Long blogId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
        Long uid = AuthUtil.checkLogin();
        if (title.isEmpty() || content.isEmpty()) {return false;}
        if (tagIds == null) {tagIds = Collections.EMPTY_LIST;}
        
        Blog blog = blogService.edit(uid, blogId, title, content, tagIds);
        
        return blog != null;
    }
    
    @RequestMapping("/comment")
    @ResponseBody
    public boolean comment(@RequestParam("content") String content, @RequestParam("sourceId") Long sourceId) {
        Long uid = AuthUtil.checkLogin();
        if (content.isEmpty()) {return false;}
        
        tweetPostService.comment(uid, content, sourceId);
        return true;
    }
}
