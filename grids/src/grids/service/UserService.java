package grids.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import grids.entity.Tag;
import grids.entity.User;
import grids.repos.FollowRepos;
import grids.repos.UserRepos;
import grids.transfer.UserSelf;
import grids.transfer.TagLabel;
import grids.transfer.UserCard;

@Service
@Transactional(readOnly=true)
public class UserService {
	@Autowired
	UserRepos userRepos;
	@Autowired
	FollowRepos followRepos;
	@Autowired
	TagService tagService;
	
	public UserSelf getSelf(long userId) {
		return new UserSelf(userRepos.get(userId),
				followRepos.followingCount(userId),
				followRepos.followerCount(userId),
				userTopTags(userId));
	}
	
	public UserCard getUserCard(long userId) {
		User user = userRepos.get(userId);
		if (user == null) {
			return null;
		}
		return new UserCard(user,
				followRepos.followingCount(userId),
				followRepos.followerCount(userId));
	}

	public User login(String email, String password) {
		User user = userRepos.findByEmail(email);
		if (user == null) {
			return null;
		}		
		if (encrypt(password).equals(user.getPassword())) {
			return user;
		} else return null;
	}
	
	@Transactional(readOnly=false)
	public long register(User user) {
		if (existsEmail(user)) {
			return -1;
		}
		//XXX existsName?
		
		userRepos.save(user);
		return user.getId();
	}
	
	private Collection<TagLabel> userTopTags(long userId) {
		Collection<TagLabel> topTags = new ArrayList<>();
		//TODO true top tags
		for (Tag tag : tagService.children(1)) {
			topTags.add(new TagLabel(tag));
		}
		return topTags;
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
