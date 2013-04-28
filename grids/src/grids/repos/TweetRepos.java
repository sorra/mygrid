package grids.repos;

import grids.entity.Tag;
import grids.entity.Tweet;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TweetRepos extends BaseRepos<Tweet> {
	@Autowired
	private TagRepos tagRepos;

	public List<Tweet> tweetsByAuthor(long authorId) {
		Query query = session().createQuery("from Tweet t where t.author.id=:authorId")
				.setLong("authorId", authorId);
		return query.list();
	}
	
	public List<Tweet> tweetsByAuthor(long authorId, Collection<Tag> tags) {
		List<Tweet> tweets = tweetsByAuthor(authorId);
		if (tags.isEmpty()) {
			return tweets;
		}
		else {
			Collection<Tag> queryTags = tagRepos.getQueryTags(tags);
			for (Iterator<Tweet> iter = tweets.iterator();iter.hasNext();) {
				if (tagRepos.noMatch(iter.next().getTags(), queryTags)) {
					iter.remove();
				}
			}
			return tweets;
		}
	}
	
//	public List<Tweet> tweetsByTag(long tagId) {
//		
//		return null;
//	}
	
	public List<Tweet> findByOrigin(long originId) {
		Query query = session().createQuery(
				"from Tweet t where t.origin.id = :originId")
				.setLong("originId", originId);
		return query.list();
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
