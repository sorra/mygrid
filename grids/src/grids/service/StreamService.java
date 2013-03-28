package grids.service;

import grids.domain.TweetOnIdComparator;
import grids.entity.Follow;
import grids.entity.Tweet;
import grids.transfer.Stream;
import grids.repos.*;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class StreamService {
	@Autowired
	private FollowRepos followRepos;
	@Autowired
	private TweetRepos tweetRepos;
	
	public Stream istream(long userId) {
		Stream stream = new Stream(userId);
		
		//XXX consider fetch_size limit
		stream.getTweets().addAll(tweetRepos.tweets(userId));
		List<Follow> followings = followRepos.followings(userId);
		for (Follow follow : followings) {
			List<Tweet> tweets = tweetRepos.tweets(follow.getTarget().getId(), follow.getTags());
			stream.getTweets().addAll(tweets);
		}
		higherSort(stream);
		return stream;
	}
	
	private void higherSort(Stream stream) {
		stream.getTweets();
		//XXX Combine & Pull-near
		Collections.sort(stream.getTweets(), new TweetOnIdComparator());
	}

}
