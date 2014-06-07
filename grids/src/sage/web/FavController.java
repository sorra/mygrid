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
import sage.transfer.FavInfo;
import sage.web.auth.AuthUtil;

@Controller
@RequestMapping("/fav")
public class FavController {
  public final String TWEET_PR = "tweet:";
  
  @Autowired
  private FavService favService;
  
  @RequestMapping(value="/add", method=RequestMethod.POST)
  public void addFav(@RequestParam(value = "link", required = false) String link,
      @RequestParam(value = "tweetId", required = false) Long tweetId) {
    Long uid = AuthUtil.checkCurrentUid();

    if (link != null && tweetId == null) {
      favService.addFav(link, uid);
    } else if (tweetId != null && link == null) {
      favService.addFav(TWEET_PR + tweetId, uid);
    }
  }
  
  @RequestMapping(value="/")
  public Collection<FavInfo> favs() {
    Long uid =AuthUtil.checkCurrentUid();
    
    Collection<FavInfo> favItems = new ArrayList<>();
    for (Fav fav : favService.favs(uid)) {
      favItems.add(new FavInfo(fav));
    }
    return favItems;
  }
}
