package grids.repos;

import grids.entity.User;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepos extends BaseRepos<User> {

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
