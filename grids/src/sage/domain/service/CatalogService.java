package sage.domain.service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sage.domain.repository.nosql.BaseCouchbaseRepository;
import sage.domain.repository.nosql.FollowCatalogRepository;
import sage.domain.repository.nosql.ResourceCatalogRepository;
import sage.entity.nosql.Catalog;
import sage.entity.nosql.FollowCatalog;
import sage.entity.nosql.ResourceCatalog;

@Service
public class CatalogService {
  @Autowired
  private ResourceCatalogRepository resourceCatalogRepo;
  @Autowired
  private FollowCatalogRepository followCatalogRepo;
  
  public ResourceCatalog getResourceCatalog(String key) {
    return resourceCatalogRepo.get(key);
  }
  
  public Boolean addResourceCatalog(ResourceCatalog rc) {
    return addCatalog(rc, resourceCatalogRepo);
  }
  
  public Boolean updateResourceCatalog(ResourceCatalog rc) {
    try {
      return resourceCatalogRepo.set(rc.getId(), rc).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
  
  public FollowCatalog getFollowCatalog(String key) {
    return followCatalogRepo.get(key);
  }
  
  public Boolean addFollowCatalog(FollowCatalog fc) {
    return addCatalog(fc, followCatalogRepo);
  }
  
  public Boolean updateFollowCatalog(FollowCatalog fc) {
    try {
      return followCatalogRepo.set(fc.getId(), fc).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
  
  private <T extends Catalog> Boolean addCatalog(Catalog catalog, BaseCouchbaseRepository<T> repo) {
    long time = System.currentTimeMillis();
    String id = generateId(catalog, time);
    try {
      Boolean success = repo.add(id, (T) catalog).get();
      if (success) {
        return success;
      } else {
        // Retry once
        id = generateId(catalog, time+1);
        return repo.add(id, (T) catalog).get();
      }
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
  
  private String generateId(Catalog rc, long time) {
    String id = rc.getOwnerId() + "_" + Long.toHexString(time);
    rc.setId(id);
    return id;
  }
}
