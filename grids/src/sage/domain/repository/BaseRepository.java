package sage.domain.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRepository<T> {
  @Autowired
  private SessionFactory sessionFactory;

  // Another approach is generic-type reflection
  protected abstract Class<T> getEntityClass();

  protected Session session() {
    return sessionFactory.getCurrentSession();
  }

  public T load(long id) {
    return (T) session().load(getEntityClass(), id);
  }

  public T get(long id) {
    return (T) session().get(getEntityClass(), id);
  }

  public void save(T entity) {
    session().save(entity);
  }

  public void update(T entity) {
    session().update(entity);
  }

  public void merge(T entity) {
    session().saveOrUpdate(entity);
  }

  public void delete(T entity) {
    session().delete(entity);
  }
}
