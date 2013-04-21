package grids.transfer;

import java.util.List;

import grids.entity.Tag;

public class TagLabel {
	private long id;
	private String name;
	private String chainStr;
	public TagLabel(Tag tag) {
		id = tag.getId();
		name = tag.getName();
		
		List<Tag> chainUp = tag.chainUp();
		if (chainUp.isEmpty()) {
			chainStr = "";
		}
		else {
			StringBuilder sb = new StringBuilder();
			for (int i = chainUp.size()-1; i >= 0; i--) {
				sb.append(chainUp.get(i));
				if(i > 0) sb.append("->");
			}
			chainStr = sb.toString();
		}
	}
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getChainStr() {
		return chainStr;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
