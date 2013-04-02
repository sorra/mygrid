package grids.transfer;

import grids.entity.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stream {
	private long uid;
	private List<TweetCard> items = new ArrayList<>();

	public Stream(long uid) {
		this.uid = uid;
	}
	
	public void add(Tweet tweet) {
		getItems().add(new TweetCard(tweet));
	}
	
	public void addAll(Collection<Tweet> tweets) {
		for (Tweet t : tweets) {
			getItems().add(new TweetCard(t));
		}
	}
	
	public long getUid() {
		return uid;
	}

	public List<TweetCard> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "UID: " + uid + " Tweets count: " + items.size();
	}
}
