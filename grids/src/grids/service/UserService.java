package grids.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grids.entity.User;
import grids.repos.UserRepos;

@Service
@Transactional
public class UserService {
	@Autowired
	UserRepos userRepos;
	
	/**
	 * Prefer load() for stub proxy
	 */
	@Transactional(readOnly=true)
	public User get(Long id) {
		return userRepos.get(id);
	}

	@Transactional(readOnly=true)
	public User getByName(String name) {
		return userRepos.findByName(name);
	}

	@Transactional(readOnly=true)
	public User login(String username, String password) {
		User user = getByName(username);
		if (user == null) {
			return null;
		}
		
		if (encrypt(password).equals(user.getPassword())) {
			return user;
		} else return null;
	}
	
	public long register(User user) {
		if (existsUsername(user)) {
			return -1;
		}
		
		userRepos.save(user);
		return user.getId();
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
