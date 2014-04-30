package sage.domain;

import java.util.Comparator;

import sage.entity.Tweet;
import sage.transfer.TweetCard;

public abstract class Comparators {
  /**
   * Inverse order (larger on front)
   */
  public static Comparator<Tweet> tweetOnId() {
    return tweetOnIdComparator;
  }
  
  /**
   * Inverse order (larger on front)
   */
  public static Comparator<TweetCard> tweetCardOnId() {
    return tweetCardOnIdComparator;
  }
  
  /**
   * Inverse order (larger on front)
   */
  private static int compareId(long id1, long id2) {
    if (id1 > id2) return -1;
    else if (id1 < id2) return 1;
    else return 0;
  }

  private static Comparator<Tweet> tweetOnIdComparator = new Comparator<Tweet>() {
    @Override
    public int compare(Tweet o1, Tweet o2) {
      return compareId(o1.getId(), o2.getId());
    }
  };
  
  private static Comparator<TweetCard> tweetCardOnIdComparator = new Comparator<TweetCard>() {
    @Override
    public int compare(TweetCard o1, TweetCard o2) {
      return compareId(o1.getId(), o2.getId());
    }
  };
}
