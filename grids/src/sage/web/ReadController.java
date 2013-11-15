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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sage.domain.Edge;
import sage.domain.service.BlogReadService;
import sage.domain.service.StreamService;
import sage.domain.service.TweetReadService;
import sage.entity.Comment;
import sage.transfer.CommentCard;
import sage.transfer.Stream;
import sage.transfer.TweetCard;
import sage.web.auth.AuthUtil;

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
	public Stream istream(
	        @RequestParam(value="before", required=false) Long beforeId,
	        @RequestParam(value="after", required=false) Long afterId) {
		Long uid = AuthUtil.checkLogin();
		logger.info("before {}, after {}", beforeId, afterId);
		return streamService.istream(uid, getEdge(beforeId, afterId));
	}
	
	@RequestMapping("/connect/{blogId}")
	@ResponseBody
	public Stream connect(@PathVariable("blogId") Long blogId) {
		List<TweetCard> tcs = tweetReadService.connectTweets(blogId);
		return new Stream(tcs);
	}
	
	@RequestMapping("/{id}/comments")
	@ResponseBody
	public List<CommentCard> comments(@PathVariable("id") Long tweetId) {
		List<CommentCard> list = new ArrayList<>();
		for (Comment comment : tweetReadService.getComments(tweetId)) {
			list.add(new CommentCard(comment));
		}
		return list;
	}
	
	@RequestMapping("/tag/{id}")
	@ResponseBody
	public Stream tagStream(
			@PathVariable("id") Long tagId,
			@RequestParam(value="before", required=false) Long beforeId,
            @RequestParam(value="after", required=false) Long afterId) {
		return streamService.tagStream(tagId, getEdge(beforeId, afterId));
	}
	
	@RequestMapping("/u/{id}")
	@ResponseBody
	public Stream personalStream(
			@PathVariable("id") Long userId,
            @RequestParam(value="before", required=false) Long beforeId,
            @RequestParam(value="after", required=false) Long afterId) {
		return streamService.personalStream(userId, getEdge(beforeId, afterId));
	}
	
    private Edge getEdge(Long beforeId, Long afterId) {
        if (beforeId == null && afterId == null) {
            return Edge.none();
        }
        else if (beforeId != null && afterId != null) {
            throw new UnsupportedOperationException();
        }
        else if (beforeId != null) {
            return Edge.before(beforeId);
        }
        else if (afterId != null) {
            return Edge.after(afterId);
        }
        throw new UnsupportedOperationException();
    }
}
