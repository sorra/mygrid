package grids.transfer;

import grids.entity.Tweet;

import java.util.ArrayList;
import java.util.List;

public class Stream {
	private List<Tweet> tweets = new ArrayList<>();

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	@Override
	public String toString() {
		return super.toString() + " Tweets count: " + tweets.size();
	}
}
