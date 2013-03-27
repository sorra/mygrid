package grids.transfer;

import grids.entity.Tag;

import java.util.List;

public class TagCard {
	private long id;
	private String name;
	private List<Tag> chainUp;
	
	public TagCard(Tag tag) {
		id = tag.getId();
		name = tag.getName();
		chainUp = tag.chainUp();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Tag> getChainUp() {
		return chainUp;
	}
}
