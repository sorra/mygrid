package sage.entity.nosql;

import java.util.ArrayList;
import java.util.List;

/**
 * Stored in NoSQL
 */
public class ResourceCatalog {
  private String id;
  private Long ownerId;
  private String name;
  private List<ResourceInfo> list;
  
  public ResourceCatalog(String id, Long ownerId, String name) {
    this.id = id;
    this.ownerId = ownerId;
    this.name = name;
    list = new ArrayList<>();
  }
  
  public String getId() {
    return id;
  }
  public Long getOwnerId() {
    return ownerId;
  }
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  public List<ResourceInfo> getList() {
    return list;
  }
}
