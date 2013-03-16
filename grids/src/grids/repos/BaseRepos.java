package grids.repos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseRepos<T> {
	@Autowired
	private SessionFactory sessionFactory;
	
	protected abstract Class<T> getEntityClass();
	
	protected Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public T load(long id) {
		return (T) session().load(getEntityClass(), id);
	}
	
	@SuppressWarnings("unchecked")
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
		session().merge(entity);
	}
	
	public void delete(T entity) {
		session().delete(entity);
	}
}
