package grids.domain;

import grids.entity.Tweet;

import java.util.Comparator;

/**
 * Inverse order (larger on front)
 */
public class TweetOnIdComparator implements Comparator<Tweet> {

	@Override
	public int compare(Tweet o1, Tweet o2) {
		if(o1.getId() > o2.getId()) return -1;
		else if(o1.getId() < o2.getId()) return 1;
		else return 0;
	}

}
