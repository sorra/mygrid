package grids.repository;

import grids.entity.Tag;
import grids.entity.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TweetRepository extends BaseRepository<Tweet> {
	private static final int MAX_RESULTS = 20;
	@Autowired
	private TagRepository tagRepos;

	public List<Tweet> byTags(Collection<Tag> tags) {
		tags = TagRepository.getQueryTags(tags);
		return session().createQuery(
				"select t from Tweet t join t.tags ta where ta in :tags")
				.setParameterList("tags", tags)
				.setMaxResults(MAX_RESULTS)
				.list();
	}
	
	public List<Tweet> byTag(Tag tag) {
		Collection<Tag> qtags = TagRepository.getQueryTags(tag);
		return byTags(qtags);
	}
	
	public List<Tweet> byAuthor(long authorId) {
		return session().createQuery("from Tweet t where t.author.id=:authorId")
				.setLong("authorId", authorId)
				.setMaxResults(MAX_RESULTS)
				.list();
	}
	
	public List<Tweet> byAuthorAndTags(long authorId, Collection<Tag> tags) {
		if (tags.isEmpty()) {
			return new ArrayList<>();
		}
		for (Tag tag : tags) {
			if (tag.getId() == Tag.ROOT_ID) {
				return byAuthor(authorId);
			}
		}
		tags = TagRepository.getQueryTags(tags);
		return session().createQuery(
				"select t from Tweet t join t.tags ta where t.author.id=:authorId and ta in :tags")
				.setLong("authorId", authorId)
				.setParameterList("tags", tags)
				.setMaxResults(MAX_RESULTS)
				.list();
	}
	
	public List<Tweet> connectTweets(long blogId) {
		Query queryShares = session().createQuery(
				"from Tweet t where t.blogId = :bid")
				.setLong("bid", blogId);
		List<Tweet> shares = queryShares.list();
		
		List<Tweet> connected = new ArrayList<>(shares);
		
		if (shares.size() > 0) {
			Set<Long> originIds = new HashSet<>();
			for (Tweet origin : shares) {
				originIds.add(origin.getId());
			}
			Query queryReshares = session().createQuery(
					"from Tweet t where t.origin.id in :ids")
					.setParameterList("ids", originIds);
			connected.addAll(queryReshares.list());
		}
		
		return connected;
	}
	
	public List<Tweet> byOrigin(long originId) {
		return session().createQuery(
				"from Tweet t where t.origin.id = :originId")
				.setLong("originId", originId)
				.list();
	}
	
	public long forwardCount(long originId) {
		Query query = session().createQuery(
				"select count(*) from Tweet t  where t.origin.id = :originId")
				.setLong("originId", originId);
		return (long) query.uniqueResult();
	}

	@Override
	protected Class<Tweet> getEntityClass() {
		return Tweet.class;
	}
}
