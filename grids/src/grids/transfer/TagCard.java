package grids.transfer;

import grids.entity.Tag;

import java.util.List;

public class TagCard {
	private long id;
	private String name;
	private List<TagCard> chainUp;
	
	public TagCard(Tag tag) {
		id = tag.getId();
		name = tag.getName();
		for (Tag node : tag.chainUp()) {
			chainUp.add(new TagCard(node));
		}
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<TagCard> getChainUp() {
		return chainUp;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
