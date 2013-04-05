package grids.transfer;

import grids.entity.Tag;

public class TagLabel {
	private long id;
	private String name;
	public TagLabel(Tag tag) {
		id = tag.getId();
		name = tag.getName();
	}
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
