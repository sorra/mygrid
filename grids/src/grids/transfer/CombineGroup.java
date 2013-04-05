package grids.transfer;

import java.util.ArrayList;
import java.util.List;

public class CombineGroup implements Item {
	private long originId;
	private TweetCard origin;
	private List<TweetCard> tweets = new ArrayList<>();
	
	public CombineGroup(long originId, TweetCard first) {
		this.originId = originId;
		tweets.add(first);
	}
	public CombineGroup(TweetCard origin) {
		this.originId = origin.getId();
		this.origin = origin;
	}
	
	public TweetCard singleMember() {
		if (origin != null && tweets.size() == 0) {
			return origin;
		}
		else if (origin == null & tweets.size() == 1) {
			return tweets.get(0);
		}
		else return null;
	}
	
	public long getOriginId() {
		return originId;
	}
	public TweetCard getOrigin() {
		return origin;
	}
	public void setOrigin(TweetCard origin) {
		this.origin = origin;
	}
	public List<TweetCard> getTweets() {
		return tweets;
	}
}
