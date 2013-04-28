package grids.repos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import grids.entity.Blog;
import grids.entity.Tag;
import grids.entity.Tweet;

@Repository
public class BlogRepos extends BaseRepos<Blog> {
	private static final String BLOG_PATH = "/blog/";

	@Autowired
	private TagRepos tagRepos;
	
	public List<Blog> blogs(Collection<Tag> tags) {
		List<Blog> blogs = session().createQuery("from Blog b").list();
		
		Collection<Tag> queryTags = tagRepos.getQueryTags(tags);
		for (Iterator<Blog> iter = blogs.iterator(); iter.hasNext();) {
			if (tagRepos.noMatch(iter.next().getTags(), queryTags)) {
				iter.remove();
			}
		}
		return blogs;
	}
	
	public List<Tweet> connectTweets(long blogId) {
		String sourceUrl = BLOG_PATH + blogId;
		Query queryShares = session().createQuery(
				"from Tweet t where t.sourceUrl = :url")
				.setString("url", sourceUrl);
		List<Tweet> shares = queryShares.list();
		
		Set<Long> originIds = new HashSet<>();
		for (Tweet origin : shares) {
			originIds.add(origin.getId());
		}
		Query queryReshares = session().createQuery(
				"from Tweet t where t.origin.id in :ids")
				.setParameterList("ids", originIds);
		
		List<Tweet> connected = new ArrayList<>(shares);
		connected.addAll(queryReshares.list());		
		return connected;
	}

	@Override
	protected Class<Blog> getEntityClass() {
		return Blog.class;
	}
	
}
