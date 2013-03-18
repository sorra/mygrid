package grids.transfer;

import grids.entity.Tweet;

import java.util.ArrayList;
import java.util.List;

public class Stream {
	private long uid;
	private List<Tweet> tweets = new ArrayList<>();

	public Stream(long uid) {
		this.uid = uid;
	}
	
	public long getUid() {
		return uid;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	@Override
	public String toString() {
		return "UID: " + uid + " Tweets count: " + tweets.size();
	}
}
