package sage.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sage.domain.service.BlogReadService;
import sage.domain.service.StreamService;
import sage.domain.service.TweetReadService;
import sage.entity.Comment;
import sage.transfer.CommentCard;
import sage.transfer.Stream;
import sage.transfer.TweetCard;

@Controller
@RequestMapping("/read")
public class ReadController {
	private final static Logger logger = LoggerFactory.getLogger(ReadController.class);
	@Autowired
	private StreamService streamService;
	@Autowired
	private BlogReadService blogReadService;
	@Autowired
	private TweetReadService tweetReadService;
	
	@RequestMapping("/istream")
	@ResponseBody
	public Stream istream(HttpSession session) {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null) {
			logger.info("not logged in");
			return null;
		}
		return streamService.istream(uid);
	}
	
	@RequestMapping("/connect/{blogId}")
	@ResponseBody
	public List<TweetCard> connect(@PathVariable("blogId") long blogId) {
		return tweetReadService.connectTweets(blogId);
	}
	
	@RequestMapping("/{id}/comments")
	@ResponseBody
	public List<CommentCard> comments(@PathVariable("id") long tweetId) {
		List<CommentCard> list = new ArrayList<>();
		for (Comment comment : tweetReadService.getComments(tweetId)) {
			list.add(new CommentCard(comment));
		}
		return list;
	}
	
	@RequestMapping("/tag/{id}")
	@ResponseBody
	public Stream tagStream(HttpSession session,
			@PathVariable("id") long tagId) {
		return streamService.tagStream(tagId);
	}
	
	@RequestMapping("/u/{id}")
	@ResponseBody
	public Stream personalStream(HttpSession session,
			@PathVariable("id") long userId) {
		return streamService.personalStream(userId);
	}
}
