package grids.repos;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import grids.entity.Comment;

@Repository
public class CommentRepos extends BaseRepos<Comment> {

	public List<Comment> findBySource(long sourceId) {
		Query query = session().createQuery(
				"from Comment c where c.source.id = :sourceId")
				.setLong("sourceId", sourceId);
		return query.list();
	}
	
	public long commentCount(long sourceId) {
		Query query = session().createQuery(
				"select count(*) from Comment c where c.source.id = :sourceId")
				.setLong("sourceId", sourceId);
		return (long) query.uniqueResult();
	}
	
	@Override
	protected Class<Comment> getEntityClass() {
		return Comment.class;
	}

}
