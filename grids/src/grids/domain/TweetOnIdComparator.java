package grids.domain;

import grids.transfer.TweetCard;

import java.util.Comparator;

/**
 * Inverse order (larger on front)
 */
public class TweetOnIdComparator implements Comparator<TweetCard> {

	@Override
	public int compare(TweetCard o1, TweetCard o2) {
		if(o1.getId() > o2.getId()) return -1;
		else if(o1.getId() < o2.getId()) return 1;
		else return 0;
	}

}
