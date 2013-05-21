 package grids.web;

import java.util.ArrayList;
import java.util.Collection;

import grids.entity.Blog;
import grids.entity.Tweet;
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
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TweetPostService tweetPostService;
	@Autowired
	private BlogPostService blogService;
	
	@RequestMapping(value="/tweet", method=RequestMethod.POST)
	@ResponseBody
	public boolean tweet(HttpSession session,
			@RequestParam("content") String content, 
			@RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null) {return false;}
		if (content.isEmpty()) {return false;}
		if (tagIds == null) {tagIds = new ArrayList<>(0);}
		
		Tweet tweet = tweetPostService.newTweet(uid, content, tagIds);
		logger.info("post tweet {} success", tweet.getId());
		return true;
	}
	
	@RequestMapping(value="/blog", method=RequestMethod.POST)
	@ResponseBody
	public boolean blog(HttpSession session,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null) {return false;}
		if (title.isEmpty() || content.isEmpty()) {return false;}
		if (tagIds == null) {tagIds = new ArrayList<>(0);}
		
		Blog blog = blogService.newBlog(uid, title, content, tagIds);
		tweetPostService.share(uid, blog);
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
