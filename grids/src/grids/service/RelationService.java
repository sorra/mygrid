package grids.service;

import grids.entity.Follow;
import grids.entity.User;
import grids.repos.*;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RelationService {
	@Autowired
	UserRepos userRepos;
	@Autowired
	FollowRepos followRepos;
	@Autowired
	TagRepos tagRepos;

	public void follow(long userId, long targetId, long[] tagIds) {
		User user = userRepos.load(userId);
		Follow follow = new Follow(user, userRepos.load(targetId), tagRepos.getTags(tagIds));
		followRepos.save(follow);
	}
	
	/*
	 * May optimize it by findByFollowId (surrogate key)
	 */
	public void editFollow(long userId, long targetId, long[] tagIds) {
		Follow follow = followRepos.find(userId, targetId);
		follow.setTags(tagRepos.getTags(tagIds));
	}
	
	public void unfollow(long userId, long targetId) {
		Follow follow = followRepos.find(userId, targetId);
		followRepos.delete(follow);
	}
	
	@Transactional(readOnly=true)
	public Follow getFollow(long sourceId, long targetId) {
		return followRepos.find(sourceId, targetId);
	}
	
	@Transactional(readOnly=true)
	public Collection<Follow> followings(long userId) {
		return followRepos.followings(userId);
	}

	@Transactional(readOnly=true)
	public Collection<Follow> followers(long userId) {
		return followRepos.followers(userId);
	}
}
