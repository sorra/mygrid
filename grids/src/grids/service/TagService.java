package grids.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import grids.entity.Follow;
import grids.entity.Tag;

@Service
public class TagService {
	public void resetFollowTags(List<Tag> tags) {
		new Follow().setTags(tags);
	}
	
	public List<Tag> chainUp(Tag leaf) {
		return null;
	}
	
	Collection<Tag> children(Tag tag) {
		return null;
	}
	
	Collection<Tag> descendants(Tag tag) {
		return null;
	}
}
