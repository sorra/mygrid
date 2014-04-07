package sage.transfer;

import sage.entity.User;

public class UserLabel {
  private long id;
  private String name;
  private String avatar;

  public UserLabel(User user) {
    this(user.getId(), user.getName(), user.getAvatar());
  }

  public UserLabel(long _id, String _name, String _avatar) {
    id = _id;
    name = _name;
    avatar = _avatar;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAvatar() {
    return avatar;
  }
}
