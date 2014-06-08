package sage.domain.repository.nosql;

import org.springframework.stereotype.Repository;

import sage.entity.nosql.FollowCatalog;

@Repository
public class FollowCatalogRepository extends BaseCouchbaseRepository<FollowCatalog> {
  @Override
  public FollowCatalog get(String id) {
    FollowCatalog fc = super.get(id);
    fc.setId(id);
    return fc;
  }
}
