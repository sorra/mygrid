package grids.web;

import grids.service.BlogService;
import grids.service.TweetService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private TweetService tweetService;
	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value="/tweet", method=RequestMethod.POST)
	@ResponseBody
	public boolean tweet(HttpSession session,
			@RequestParam("content") String content, 
			@RequestParam("tagIds") long[] tagIds) {
		long uid = (long) session.getAttribute(SessionKeys.UID);
		tweetService.tweet(uid, content, tagIds);
		return true;
	}
	
	@RequestMapping(value="/blog", method=RequestMethod.POST)
	@ResponseBody
	public boolean blog(HttpSession session,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("tagIds") long[] tagIds) {
		long uid = (Long) session.getAttribute(SessionKeys.UID);
		blogService.blog(uid, title, content, tagIds);
		return true;
	}
}
