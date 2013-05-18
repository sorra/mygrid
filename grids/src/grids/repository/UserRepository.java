package grids.repository;

import grids.entity.User;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<User> {

	public User findByEmail(String email) {
		Query query = session().createQuery("from User u where u.email=:email")
				.setString("email", email);
		return (User) query.uniqueResult();
	}
	
	public User findByName(String name) {
		Query query = session().createQuery("from User u where u.name=:name")
				.setString("name", name);
		return (User) query.uniqueResult();
	}
	
	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
}
