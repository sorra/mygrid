package grids.service;

import java.util.Collections;
import java.util.List;

import grids.entity.Tag;
import grids.entity.Tweet;
import grids.repos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {
	@Autowired
	private UserRepos userRepos;
	
	public Tweet get(long tweetId) {
		return new Tweet("TuT...", null, null, Collections.<Tag>emptyList());
	}
	
	public void tweet(long userId, String content, long[] tags) {
		new Tweet(content, userRepos.get(userId), null, Collections.<Tag> emptyList());
	}
	
	public void comment(long originId) {
		
	}
	
	public List<?> comments(Tweet source) {
		return null;
	}
	
	public void share() {
		
	}
	
	public void reshare(Tweet origin) {
		
	}
	
	public void delete(Tweet t) {
		
	}
}
