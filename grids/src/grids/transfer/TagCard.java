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

	/**
	 * @see Tag#chainUp()
	 */
	public List<TagCard> getChainUp() {
		return chainUp;
	}
	
	@Override
	public String toString() {
		if (chainUp.isEmpty()) {
			return name;
		}
		
		StringBuilder sb= new StringBuilder();
		for (int i = chainUp.size()-1; i > 0; i--) {
			sb.append(chainUp.get(i)).append("->");
		}
		sb.append(chainUp.get(0));
		return sb.toString();
	}
}
