package grids.repos;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import grids.entity.Tag;
import grids.entity.Tweet;

@Repository
public class TweetRepos extends BaseRepos<Tweet> {

	@Override
	protected Class<Tweet> getEntityClass() {
		return Tweet.class;
	}

	public List<Tweet> tweets(long authorId) {
		Query query = session().createQuery("from Tweet t where t.author.id=?")
				.setLong(0, authorId);
		return query.list();
	}
	
	public List<Tweet> tweets(long authorId, List<Tag> tags) {
		List<Long> tagIds = new ArrayList<>();
		for (Tag tag : tags) {
			tagIds.add(tag.getId());
		}
		Query query = session().createQuery(
			"select tw from Tweet tw, tw.tags tag  where tw.author.id=:authorId and tag.id in :tagIds")
			.setLong("authorId", authorId)
			.setParameterList("tagIds", tagIds);
		return query.list();
	}
}
