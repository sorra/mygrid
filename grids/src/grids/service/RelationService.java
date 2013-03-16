package grids.service;

import grids.entity.Follow;
import grids.entity.User;
import grids.repos.*;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelationService {
	@Autowired
	UserRepos userRepos;
	@Autowired
	FollowRepos followRepos;
	@Autowired
	TagRepos tagRepos;

	public void follow(long userId, long targetId, long[] tagIds) {
		User user = userRepos.load(userId);
		Follow follow = new Follow(user, userRepos.load(targetId), tagRepos.tags(tagIds));
		followRepos.save(follow);
	}
	
	public void resetFollow(long userId, long targetId, long[] tagIds) {
		User user = userRepos.load(userId);
		Follow follow = new Follow(user, userRepos.load(targetId), tagRepos.tags(tagIds));
		followRepos.merge(follow);
	}
	
	public void unfollow(long userId, long targetId) {
		Follow follow = followRepos.find(userId, targetId);
		followRepos.delete(follow);
	}
	
	public Collection<Follow> followings(long userId) {
		return followRepos.followings(userId);
	}
	
	public Collection<Follow> followers(long userId) {
		return followRepos.followers(userId);
	}
}
