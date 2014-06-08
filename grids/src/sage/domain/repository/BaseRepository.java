package sage.domain.repository;

import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRepository<T> {
  protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  @Autowired
  private SessionFactory sessionFactory;

  protected Session session() {
    return sessionFactory.getCurrentSession();
  }

  public T load(long id) {
    return (T) session().load(entityClass, id);
  }

  public T get(long id) {
    return (T) session().get(entityClass, id);
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
