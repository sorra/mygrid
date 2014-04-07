package sage.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sage.domain.Edge;
import sage.domain.TweetOnIdComparator;
import sage.entity.Tweet;
import sage.transfer.CombineGroup;
import sage.transfer.Item;
import sage.transfer.Stream;
import sage.transfer.TweetCard;

@Service
public class StreamService {
  @Autowired
  private TweetReadService tweetReadService;
  @Autowired
  private TagService tagService;
  @Autowired
  private TransferService transferService;

  public Stream istream(long userId) {
    return istream(userId, Edge.none());
  }

  public Stream istream(long userId, Edge edge) {
    List<TweetCard> tcs = tweetReadService.istream(userId, edge);
    return new Stream(higherSort(tcs));
  }

  private List<Item> higherSort(List<TweetCard> tcs) {
    List<Item> cleanList = new ArrayList<>();
    cleanList.addAll(tcs);

    // TODO Pull-near
    return combine(tcs);
  }

  private List<Item> combine(List<TweetCard> tcs) {
    List<CombineGroup> groupSeq = new ArrayList<>();
    for (TweetCard tc : tcs) {
      if (tc.getOrigin() != null) {
        long originId = tc.getOrigin().getId();
        CombineGroup foundGroup = findInSeq(originId, groupSeq);
        if (foundGroup != null) {
          foundGroup.addForward(tc);
        }
        else {
          groupSeq.add(CombineGroup.newByFirst(tc));
        }
      }
      else {
        CombineGroup foundGroup = findInSeq(tc.getId(), groupSeq);
        if (foundGroup != null) {
          foundGroup.addOrigin(tc);
        }
        else {
          groupSeq.add(CombineGroup.newByOrigin(tc));
        }
      }
    }

    List<Item> sequence = new ArrayList<>(groupSeq.size());
    for (CombineGroup group : groupSeq) {
      if (group.getForwards().isEmpty()) {
        Assert.notNull(group.getOrigin());
        sequence.add(group.getOrigin());
      }
      else
        sequence.add(group);
    }
    return sequence;
  }

  private CombineGroup findInSeq(long id, List<CombineGroup> groupSequence) {
    for (CombineGroup group : groupSequence) {
      if (group.getOrigin().getId() == id) {
        return group;
      }
    }
    return null;
  }

  public Stream tagStream(long tagId, Edge edge) {
    List<Tweet> tweets = tweetReadService.tweetsByTags(tagService.getQueryTags(tagId), edge);
    Collections.sort(tweets, new TweetOnIdComparator());
    return new Stream(transferService.listTweetCards(tweets, false));
  }

  public Stream personalStream(long userId, Edge edge) {
    List<Tweet> tweets = tweetReadService.tweetsByAuthor(userId, edge);
    Collections.sort(tweets, new TweetOnIdComparator());
    return new Stream(transferService.listTweetCards(tweets, false));
  }

  public Stream groupStream(long groupId, Edge edge) {
    return null;
  }
}
