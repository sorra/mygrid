package sage.web;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sage.domain.service.RelationService;
import sage.web.auth.AuthUtil;

@Controller
@RequestMapping
public class RelationController {
    @Autowired
    private RelationService relationService;
    
    @RequestMapping("/follow/{id}")
    @ResponseBody
    public void follow(@PathVariable("id") Long targetId,
            @RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
        Long uid = AuthUtil.checkLogin();
        if (uid == null){return;}
        
        if (tagIds == null) {
            tagIds = Collections.EMPTY_LIST;
        }
        relationService.follow(uid, targetId, tagIds);
    }
    
    @RequestMapping("/editfollow/{id}")
    @ResponseBody
    public void editFollow(@PathVariable("id") Long targetId,
            @RequestParam(value="tagIds[]", required=false) Collection<Long> tagIds) {
        Long uid = AuthUtil.checkLogin();
        if (uid == null){return;}
        
        if (tagIds == null) {
            tagIds = Collections.EMPTY_LIST;
        }
        relationService.editFollow(uid, targetId, tagIds);
    }
    
    @RequestMapping("/unfollow/{id}")
    @ResponseBody
    private void unfollow(@PathVariable("id") Long targetId) {
        Long uid = AuthUtil.checkLogin();
        if (uid == null){return;}
        relationService.unfollow(uid, targetId);
    }
}
