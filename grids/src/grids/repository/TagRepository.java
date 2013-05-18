package grids.repository;

import grids.entity.Tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository
public class TagRepository extends BaseRepository<Tag> {
	public Tag findByNameAndParent(String name, long parentId) {
		for (Tag child : get(parentId).getChildren()) {
			if (child.getName().equals(name)) {
				return child;
			}
		}
		return null;
	}
	
	public Set<Tag> getTags(Collection<Long> ids) {
		Set<Tag> tags = new HashSet<>();
		for (long id : ids) {
			tags.add(get(id));
		}
		return tags;
	}
	
	public static Set<Tag> getQueryTags(Tag tag) {
		return tag.descendants();
	}
	
	public static Set<Tag> getQueryTags(Collection<Tag> tags) {
		Set<Tag> queryTags = new HashSet<>();
		for (Tag node : tags) {
			queryTags.add(node);
			queryTags.addAll(node.descendants());
		}
		return queryTags;
	}
	
//	public boolean noMatch(Collection<Tag> entityTags, Collection<Tag> queryTags) {
//		for (Tag queryTag : queryTags) {
//			if (entityTags.contains(queryTag)) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	@Override
	protected Class<Tag> getEntityClass() {
		return Tag.class;
	}
}
