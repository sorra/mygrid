package grids.repos;

import org.springframework.stereotype.Repository;

import grids.entity.Blog;

@Repository
public class BlogRepos extends BaseRepos<Blog> {

	@Override
	protected Class<Blog> getEntityClass() {
		return Blog.class;
	}
	
}
