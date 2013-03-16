package grids.repos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import grids.entity.Tag;

@Repository
public class TagRepos extends BaseRepos<Tag> {
	
	public List<Tag> tags(long[] ids) {
		List<Tag> tags = new ArrayList<>();
		for (long id : ids) {
			tags.add(get(id));
		}
		return tags;
	}
	
	@Override
	protected Class<Tag> getEntityClass() {
		return Tag.class;
	}
}
