package sage.transfer;

import sage.entity.Fav;

public class FavItem {
  private long id;
  private String link;
  private long ownerId;
  
  public FavItem(Fav fav) {
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
}
