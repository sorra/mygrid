package sage.domain.repository.nosql;

import org.springframework.stereotype.Repository;

import sage.entity.nosql.Notif;

@Repository
public class NotifRepository extends BaseCouchbaseRepository<Notif> {

  @Override
  protected Class<Notif> entityClass() {
    return Notif.class;
  }

}
