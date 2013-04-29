package grids.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grids.entity.User;
import grids.repos.FollowRepos;
import grids.repos.UserRepos;
import grids.transfer.UserCard;

@Service
@Transactional
public class UserService {
	@Autowired
	UserRepos userRepos;
	@Autowired
	FollowRepos followRepos;
	
	/**
	 * Prefer load() for lazy-loading
	 */
	@Transactional(readOnly=true)
	public User get(Long id) {
		return userRepos.get(id);
	}

	@Transactional(readOnly=true)
	public User login(String email, String password) {
		User user = userRepos.findByEmail(email);
		if (user == null) {
			return null;
		}		
		if (encrypt(password).equals(user.getPassword())) {
			return user;
		} else return null;
	}
	
	public long register(User user) {
		if (existsEmail(user)) {
			return -1;
		}
		//XXX existsName?
		
		userRepos.save(user);
		return user.getId();
	}
	
	@Transactional(readOnly=true)
	public UserCard userCard(long userId) {
		User user = userRepos.get(userId);
		if (user == null) {
			return null;
		}
		return new UserCard(user,
				followRepos.followingCount(userId),
				followRepos.followerCount(userId));
	}

	private boolean existsEmail(User user) {
		return userRepos.findByEmail(user.getEmail()) != null;
	}
	
	@Deprecated
	private String encrypt(String password) {
		//XXX
		return password;
	}
}
