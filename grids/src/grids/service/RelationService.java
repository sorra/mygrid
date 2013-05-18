package grids.service;

import grids.entity.Follow;
import grids.entity.User;
import grids.repository.*;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RelationService {
	@Autowired
	UserRepository userRepos;
	@Autowired
	FollowRepository followRepos;
	@Autowired
	TagRepository tagRepos;

	public void follow(long userId, long targetId, Collection<Long> tagIds) {
		User user = userRepos.load(userId);
		Follow follow = new Follow(user, userRepos.load(targetId), tagRepos.getTags(tagIds));
		followRepos.save(follow);
	}
	
	/*
	 * May optimize it by findByFollowId (surrogate key)
	 */
	public void editFollow(long userId, long targetId, Collection<Long> tagIds) {
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
