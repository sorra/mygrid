package grids.repos;

import grids.entity.Tag;
import grids.entity.Tweet;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TweetRepos extends BaseRepos<Tweet> {
	private static final int MAX_RESULTS = 20;
	@Autowired
	private TagRepos tagRepos;

	public List<Tweet> tweetsByTags(Collection<Tag> tags) {
		tags = tagRepos.getQueryTags(tags);
		return session().createQuery(
				"select t from Tweet t join t.tags ta where ta in :tags")
				.setParameterList("tags", tags)
				.setMaxResults(MAX_RESULTS)
				.list();
	}
	
	public List<Tweet> tweetsByTag(long tagId) {
		Collection<Tag> qtags = tagRepos.getQueryTags(tagRepos.get(tagId));
		return tweetsByTags(qtags);
	}
	
	public List<Tweet> tweetsByAuthor(long authorId) {
		return session().createQuery("from Tweet t where t.author.id=:authorId")
				.setLong("authorId", authorId)
				.setMaxResults(MAX_RESULTS)
				.list();
	}
	
	public List<Tweet> tweetsByAuthorAndTags(long authorId, Collection<Tag> tags) {
		tags = tagRepos.getQueryTags(tags);
		return session().createQuery(
				"select t from Tweet t join t.tags ta where t.author.id=:authorId and ta in :tags")
				.setLong("authorId", authorId)
				.setParameterList("tags", tags)
				.setMaxResults(MAX_RESULTS)
				.list();
	}
	
	public List<Tweet> findByOrigin(long originId) {
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
