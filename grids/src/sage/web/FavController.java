package sage.web;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sage.domain.service.FavService;
import sage.entity.Fav;
import sage.transfer.FavItem;
import sage.web.auth.AuthUtil;

@Controller
@RequestMapping("/fav")
public class FavController {
  @Autowired
  private FavService favService;
  
  @RequestMapping(value="/new", method=RequestMethod.POST)
  public void newFav(@RequestParam("link") String link) {
    Long uid = AuthUtil.checkCurrentUid();
    favService.newFav(link, uid);
  }
  
  @RequestMapping(value="/")
  public Collection<FavItem> favs() {
    Long uid =AuthUtil.checkCurrentUid();
    
    Collection<FavItem> favItems = new ArrayList<>();
    for (Fav fav : favService.favs(uid)) {
      favItems.add(new FavItem(fav));
    }
    return favItems;
  }
}
