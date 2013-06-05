package grids.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import grids.entity.Follow;
import grids.service.BlogReadService;
import grids.service.RelationService;
import grids.service.TagService;
import grids.service.TransferService;
import grids.service.TweetReadService;
import grids.service.UserService;
import grids.transfer.BlogData;
import grids.transfer.UserCard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	ObjectMapper om = new ObjectMapper();
	
	@RequestMapping("/public/{id}")
	public String publicPage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/private/{id}")
	public String privatePage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/group/{id}")
	public String groupPage(@PathVariable long id) {
		return null;
	}
	
	@RequestMapping("/blog/{id}")
	public String blogPage(@PathVariable long id, ModelMap model) {
		BlogData blog = blogReadService.getBlogData(id);
		if (blog == null) {
			logger.info("blog {} is null!", id);
			return "redirect:/";
		}
		
		model.addAttribute("blog", blog)
			.addAttribute("tweets", tweetReadService.connectTweets(id));
		return "blog.httl";
	}
	
	@RequestMapping("/blogs")
	public String blogs(ModelMap model) {
		model.addAttribute("blogs", blogReadService.getAllBlogData());
		return "blogs.httl";
	}
	
	@RequestMapping("/writeBlog")
	public String writeBlog(
			HttpSession session, ModelMap model) throws JsonProcessingException {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null) {return "";}
		
		String selfJson = om.writeValueAsString(userService.getSelf(uid));
		model.addAttribute("selfJson", selfJson);
		String tagTreeJson = om.writeValueAsString(tagService.getTagTree());
		model.addAttribute("tagTreeJson", tagTreeJson);
		return "write-blog.httl";
	}
	
	@RequestMapping("/followings")
	public String followings(
			HttpSession session, ModelMap model) throws JsonProcessingException {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null) {return "";}
		
		List<String> followingsInJson = new ArrayList<>();
		for (Follow follow: relationService.followings(uid)) {
			UserCard followingUc = userService.getUserCard(
					uid, follow.getTarget().getId());
			followingsInJson.add(om.writeValueAsString(followingUc));
		}
		model.addAttribute("followings", followingsInJson);
		return "followings.httl";
	}
	
	@RequestMapping("/followers")
	public String followers(HttpSession session, ModelMap model) throws JsonProcessingException {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null) {return "";}
		
		List<String> followersInJson = new ArrayList<>();
		for (Follow follow: relationService.followers(uid)) {
			UserCard followerUc = userService.getUserCard(
					uid, follow.getSource().getId());
			followersInJson.add(om.writeValueAsString(followerUc));
		}
		model.addAttribute("followers", followersInJson);
		return "followers.httl";
	}
	
	@RequestMapping("/test")
	public String test() {
		return "test.httl";
	}
}
