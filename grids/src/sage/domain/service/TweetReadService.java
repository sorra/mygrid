package sage.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sage.domain.Edge;
import sage.domain.TweetOnIdComparator;
import sage.domain.repository.CommentRepository;
import sage.domain.repository.FollowRepository;
import sage.domain.repository.TweetRepository;
import sage.entity.Comment;
import sage.entity.Follow;
import sage.entity.Tag;
import sage.entity.Tweet;
import sage.transfer.TweetCard;

@Service
@Transactional(readOnly=true)
public class TweetReadService {
	private static final int FETCH_SIZE = 20;
	@Autowired
	private TransferService transferService;
	@Autowired
	private TweetRepository tweetRepo;
	@Autowired
	private FollowRepository followRepo;
	@Autowired
	private CommentRepository commentRepo;
	
	public List<TweetCard> istream(long userId, Edge edge) {
		List<Tweet> tweets = new ArrayList<>();
		
		tweets.addAll(tweetRepo.byAuthor(userId, edge));
		
		// Find and merge tweets from followings
		List<Follow> followings = followRepo.followings(userId);
		for (Follow follow : followings) {
	        List<Tweet> result = tweetRepo.byAuthorAndTags(
	                follow.getTarget().getId(), follow.getTags(), edge);
			tweets.addAll(result);
		}
		Collections.sort(tweets, new TweetOnIdComparator());
		
		List<TweetCard> tcs = new ArrayList<>();

		// Select the top items, for later's higher sort
		List<Tweet> tops = (FETCH_SIZE < tweets.size())
				? tweets.subList(0, FETCH_SIZE) : tweets;
		for (Tweet tweet : tops) {
			// How to optimize the counting, by session cache?
			tcs.add(transferService.getTweetCard(tweet));
		}
		return tcs;
	}
	
	public List<Tweet> tweetsByTags(Collection<Tag> tags, Edge edge) {
		return new ArrayList<>(tweetRepo.byTags(tags, edge));
	}
	
	public List<Tweet> tweetsByAuthor(long authorId, Edge edge) {
		return new ArrayList<>(tweetRepo.byAuthor(authorId, edge));
	}

	public TweetCard getTweetCard(long tweetId) {
	    Tweet tweet = tweetRepo.get(tweetId);
	    if (tweet == null) {
	        return null;
	    }
		return transferService.getTweetCard(tweet);
	}

	public Collection<Tweet> getForwards(long originId) {
		return new ArrayList<>(tweetRepo.byOrigin(originId));
	}

	public Collection<Comment> getComments(long sourceId) {
		return new ArrayList<>(tweetRepo.load(sourceId).getComments());
	}
	
	/**
	 * Experimental
	 * @return a sequential list of connected tweets
	 */
	public List<TweetCard> connectTweets(long blogId) {
		List<TweetCard> tcs = new ArrayList<>();
		for (Tweet tweet : tweetRepo.connectTweets(blogId)) {
			tcs.add(transferService.getTweetCard(tweet));
		}
		return tcs;
	}
}
