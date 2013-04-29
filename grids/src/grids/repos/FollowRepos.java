package grids.repos;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import grids.entity.Follow;

@Repository
public class FollowRepos extends BaseRepos<Follow>{

	public Follow find(long sourceId, long targetId) {
		Query query = session().createQuery(
				"from Follow f where f.source.id=:sourceId and f.target.id=:targetId")
				.setLong("sourceId", sourceId).setLong("targetId", targetId);
		return (Follow) query.uniqueResult();
	}

	public List<Follow> followings(long userId) {
		Query query = session().createQuery(
				"from Follow f where f.source.id=:sourceId")
				.setLong("sourceId", userId);
		return query.list();
	}
	
	public List<Follow> followers(long userId) {
		Query query = session().createQuery(
				"from Follow f where f.target.id=:targetId")
				.setLong("targetId", userId);
		return query.list();
	}
	
	public int followingCount(long userId) {
		return followings(userId).size();
	}
	
	public int followerCount(long userId) {
		return followers(userId).size();
	}
	
	@Override
	protected Class<Follow> getEntityClass() {
		return Follow.class;
	}
}
