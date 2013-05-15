package grids.transfer;

import grids.entity.Tag;

import java.util.ArrayList;
import java.util.Collection;

public class TagNode {
	private Long id;
	private String name;
	private Collection<TagNode> children = new ArrayList<>();
	
	public TagNode(Tag tag) {
		id = tag.getId();
		name = tag.getName();
		for (Tag child : tag.getChildren()) {
			children.add(new TagNode(child));
		}
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Collection<TagNode> getChildren() {
		return children;
	}
}
