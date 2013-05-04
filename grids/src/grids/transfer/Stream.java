package grids.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stream {
	private List<Item> items = new ArrayList<>();

	public void add(Item item) {
		getItems().add(item);
	}
	
	public void addAll(Collection<Item> items) {
		getItems().addAll(items);
	}
	public List<Item> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return " Tweets count: " + items.size();
	}
}
