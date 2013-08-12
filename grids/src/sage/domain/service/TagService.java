package sage.domain.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sage.domain.repository.TagRepository;
import sage.entity.Tag;
import sage.transfer.TagCard;
import sage.transfer.TagLabel;
import sage.transfer.TagNode;

@Service
@Transactional
public class TagService {
	@Autowired
	TagRepository tagRepos;
	
	public long newTag(String name, long parentId) {
		Tag tag = new Tag(name, tagRepos.load(parentId));
		if (tagRepos.byNameAndParent(name, parentId) == null) {
			tagRepos.save(tag);
			return tag.getId();
		}
		else return -1;
	}
	
	@Transactional(readOnly=true)
	public TagCard getTagCard(long tagId) {
		Tag tag = tagRepos.get(tagId);
		return tag==null ? null : new TagCard(tag);
	}
	
	@Transactional(readOnly=true)
	public TagLabel getTagLabel(long tagId) {
		Tag tag = tagRepos.get(tagId);
		return tag==null ? null : new TagLabel(tag);
	}

	@Transactional(readOnly=true)
	public TagNode getTagTree() {
		return new TagNode(tagRepos.get(Tag.ROOT_ID));
	}
	
	@Transactional(readOnly=true)
	public Collection<Tag> getQueryTags(long tagId) {
		return TagRepository.getQueryTags(tagRepos.get(tagId));
	}

	@Transactional(readOnly=true)
	public Collection<Tag> getTagsByName(String name) {
		return new ArrayList<>(tagRepos.byName(name));
	}
	
	public void changeParent(long id, long parentId) {
	    tagRepos.get(id).setParent(tagRepos.load(parentId));
	}

	public void init() {
		if (needInitialize) {
			tagRepos.save(new Tag("root", null));
			needInitialize = false;
		}
	}
	private static boolean needInitialize = true;
}
