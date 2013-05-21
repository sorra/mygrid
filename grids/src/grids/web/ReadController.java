package grids.web;

import java.util.List;

import grids.service.BlogReadService;
import grids.service.StreamService;
import grids.service.TweetReadService;
import grids.transfer.Stream;
import grids.transfer.TweetCard;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public List<TweetCard> connect(@PathVariable long blogId) {
		return tweetReadService.connectTweets(blogId);
	}
}
