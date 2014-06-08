package sage.entity.nosql;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Stored in NoSQL
 */
public class FollowCatalog {
  @JsonIgnore private String id;
  private Long ownerId;
  private String name;
  private List<FollowInfo> list;
  
  FollowCatalog() {}
  
  public FollowCatalog(Long ownerId, String name) {
    this.ownerId = ownerId;
    this.name = name;
    list = new ArrayList<>();
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  
  public Long getOwnerId() {
    return ownerId;
  }
  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public List<FollowInfo> getList() {
    return list;
  }
}
