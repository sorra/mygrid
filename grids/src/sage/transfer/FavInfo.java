package sage.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sage.entity.Fav;

public class FavInfo {
  private long id;
  private String link;
  private long ownerId;
  
  FavInfo() {}
  
  public FavInfo(Fav fav) {
    id = fav.getId();
    link = fav.getLink();
    ownerId = fav.getOwner().getId();
  }
  
  public long getId() {
    return id;
  }
  public String getLink() {
    return link;
  }
  public long getOwnerId() {
    return ownerId;
  }
  
  public static List<FavInfo> listOf(Collection<Fav> favs) {
    List<FavInfo> infos = new ArrayList<>();
    for (Fav fav : favs) {
      infos.add(new FavInfo(fav));
    }
    return infos;
  }
}
