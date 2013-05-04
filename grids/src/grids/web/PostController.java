package grids.web;

import grids.entity.Blog;
import grids.service.BlogPostService;
import grids.service.TweetPostService;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/post")
public class PostController {
	private final static Logger logger = LoggerFactory.getLogger(PostController.class);
	@Autowired
	private TweetPostService tweetService;
	@Autowired
	private BlogPostService blogService;
	
	@RequestMapping(value="/tweet", method=RequestMethod.POST)
	@ResponseBody
	public boolean tweet(HttpSession session,
			@RequestParam("content") String content, 
			@RequestParam(value="tagIds", required=false) long[] tagIds) {
		logger.info("post tweet");
		if (tagIds == null) tagIds = new long[0];
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null) {return false;}
		
		tweetService.newTweet(uid, content, tagIds);
		return true;
	}
	
	@RequestMapping(value="/blog", method=RequestMethod.POST)
	@ResponseBody
	public boolean blog(HttpSession session,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value="tagIds", required=false) long[] tagIds) {
		if (tagIds == null) tagIds = new long[0];
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null) {return false;}
		
		Blog blog = blogService.newBlog(uid, title, content, tagIds, true);
		if (blog != null) {
			logger.info("post blog {} success", blog.getId());
			return true;
		}
		else {
			logger.info("post blog failure");
			return false;
		}
	}
}
