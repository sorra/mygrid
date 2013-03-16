package grids.service;

import grids.entity.Follow;
import grids.entity.Tweet;
import grids.entity.User;
import grids.transfer.Stream;
import grids.repos.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamService {
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private FollowRepos followRepos;
	@Autowired
	private TweetRepos tweetRepos;
	
	public Stream istream(long userId) {
		Stream stream = new Stream();
		
		//XXX consider fetch_size limit
		stream.getTweets().addAll(tweetRepos.tweets(userId));
		List<Follow> followings = followRepos.followings(userId);
		for (Follow each : followings) {
			List<Tweet> tweets = tweetRepos.tweets(each.getTarget().getId(), each.getTags());
			stream.getTweets().addAll(tweets);
		}
		return stream;
	}

}
