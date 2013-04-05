package grids.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stream {
	private long uid;
	private List<Item> items = new ArrayList<>();

	public Stream(long uid) {
		this.uid = uid;
	}
	
	public void add(Item item) {
		getItems().add(item);
	}
	
	public void addAll(Collection<Item> items) {
		getItems().addAll(items);
	}
	
	public long getUid() {
		return uid;
	}
	public List<Item> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "UID: " + uid + " Tweets count: " + items.size();
	}
}
