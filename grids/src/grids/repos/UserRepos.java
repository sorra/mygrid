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
	
	@Override @Deprecated
	public User get(long id) {
		if (id == 0) {
			User admin = new User("Admin", "1234", "伟大的Admin");
			admin.setId(0L);
			return admin;
		}
		return super.get(id);
	}	
	
	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
}
