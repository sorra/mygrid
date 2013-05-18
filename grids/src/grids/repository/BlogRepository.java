package grids.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import grids.entity.Blog;
import grids.entity.Tag;

@Repository
public class BlogRepository extends BaseRepository<Blog> {
	private static final int MAX_RESULTS = 20;
	
	public List<Blog> all() {
		return session().createQuery("from Blog b")
				.setMaxResults(MAX_RESULTS)
				.list();
	}
	
	public List<Blog> blogs(Collection<Tag> tags) {
		tags = TagRepository.getQueryTags(tags);
		return session().createQuery(
				"select b from Blog b join b.tags ta where ta in :tags")
				.setParameterList("tags", tags)
				.setMaxResults(MAX_RESULTS)
				.list();
	}

	@Override
	protected Class<Blog> getEntityClass() {
		return Blog.class;
	}
	
}
