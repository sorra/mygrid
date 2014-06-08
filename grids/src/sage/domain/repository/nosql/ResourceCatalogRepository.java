package sage.domain.repository.nosql;

import org.springframework.stereotype.Repository;

import sage.entity.nosql.ResourceCatalog;

@Repository
public class ResourceCatalogRepository extends BaseCouchbaseRepository<ResourceCatalog> {
  @Override
  public ResourceCatalog get(String id) {
    ResourceCatalog rc = super.get(id);
    rc.setId(id);
    return rc;
  }
}
