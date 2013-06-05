package grids.service;

import grids.entity.Follow;
import grids.repository.*;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class RelationService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserRepository userRepos;
	@Autowired
	FollowRepository followRepos;
	@Autowired
	TagRepository tagRepos;

	public void follow(long userId, long targetId, Collection<Long> tagIds) {
		if (userId == targetId) {
			logger.warn("user {} should not follow himself!", userId);
			return;
		}
		Assert.isNull(followRepos.find(userId, targetId));
		Follow follow = new Follow(
				userRepos.load(userId), userRepos.load(targetId),
				tagRepos.byIds(tagIds));
		followRepos.save(follow);
	}
	
	/*
	 * May optimize it by findByFollowId (surrogate key)
	 */
	public void editFollow(long userId, long targetId, Collection<Long> tagIds) {
		Follow follow = followRepos.find(userId, targetId);
		Assert.notNull(follow);
		follow.setTags(tagRepos.byIds(tagIds));
		followRepos.merge(follow);
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
