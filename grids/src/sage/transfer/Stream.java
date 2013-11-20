package sage.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stream {
    private List<Item> items;

    public Stream() {
        items = new ArrayList<>();
    }
    
    public Stream(List<? extends Item> items) {
        this.items = new ArrayList<>(items);
    }
    
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
