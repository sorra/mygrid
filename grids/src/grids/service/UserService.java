package grids.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grids.entity.User;
import grids.repos.UserRepos;

@Service
public class UserService {
	@Autowired
	UserRepos userRepos;
	
	/**
	 * Prefer load() for stub proxy
	 */
	@Deprecated
	public User get(Long id) {
		return userRepos.get(id);
	}
	
	public User getByName(String name) {
		return userRepos.findByName(name);
	}
	
	public User login(String username, String password) {
		User user = getByName(username);
		if (user == null) {
			return null;
		}
		
		if (encrypt(password).equals(user.getPassword())) {
			return user;
		} else return null;
	}
	
	public boolean register(User user) {
		if (existsUsername(user)) {
			return false;
		}
		
		userRepos.save(user);
		return true;
	}

	private boolean existsUsername(User user) {
		return getByName(user.getName()) != null;
	}
	
	@Deprecated
	private String encrypt(String password) {
		//XXX
		return null;
	}
}
