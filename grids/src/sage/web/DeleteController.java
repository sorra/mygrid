package sage.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sage.domain.service.BlogPostService;
import sage.domain.service.TweetPostService;
import sage.web.auth.AuthUtil;

@Controller
@RequestMapping
public class DeleteController {
    @Autowired
    private TweetPostService tweetPostService;
    @Autowired
    private BlogPostService blogPostService;
    
    @RequestMapping("/tweet/{id}/delete")
    public void deleteTweet(@PathVariable("id") Long id) {
        long uid = AuthUtil.checkLogin();
        tweetPostService.delete(uid, id);
    }
    
    @RequestMapping("/blog/{id}/delete")
    public void deleteBlog(@PathVariable("id") Long id) {
        long uid = AuthUtil.checkLogin();
        blogPostService.delete(uid, id);
    }
}
