package sage.transfer;

import java.util.Collection;

import sage.entity.Follow;

public class FollowInfo {
  private UserLabel target;
  private Collection<TagLabel> tags;
  
  public FollowInfo(Follow follow) {
    target = new UserLabel(follow.getTarget());
    tags = TagLabel.listOf(follow.getTags());
  }
  
  public UserLabel getTarget() {
    return target;
  }
  
  public Collection<TagLabel> getTags() {
    return tags;
  }
}
