package grids.repos;

import org.springframework.stereotype.Repository;

import grids.entity.Comment;

@Repository
public class CommentRepos extends BaseRepos<Comment> {

	@Override
	protected Class<Comment> getEntityClass() {
		return Comment.class;
	}

}
