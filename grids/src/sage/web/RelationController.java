package sage.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sage.domain.service.RelationService;

@Controller
@RequestMapping
public class RelationController {
	@Autowired
	private RelationService relationService;
	
	@RequestMapping("/follow/{id}")
	@ResponseBody
	public void follow(HttpSession session,
			@PathVariable("id") long targetId,
			@RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null){return;}
		
		if (tagIds == null) {
			tagIds = new ArrayList<>(0);
		}
		relationService.follow(uid, targetId, tagIds);
	}
	
	@RequestMapping("/editfollow/{id}")
	@ResponseBody
	public void editFollow(HttpSession session,
			@PathVariable("id") long targetId,
			@RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null){return;}
		
		if (tagIds == null) {
			tagIds = new ArrayList<>(0);
		}
		relationService.editFollow(uid, targetId, tagIds);
	}
	
	@RequestMapping("/unfollow/{id}")
	@ResponseBody
	private void unfollow(HttpSession session, @PathVariable("id") long targetId) {
		Long uid = AuthUtil.checkLoginUid(session);
		if (uid == null){return;}
		relationService.unfollow(uid, targetId);
	}
}
