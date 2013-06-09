package sage.domain;

import java.util.Comparator;

import sage.entity.Tweet;

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
