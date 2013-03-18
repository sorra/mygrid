package grids.service;

import grids.entity.Tweet;
import grids.repos.TagRepos;
import grids.repos.TweetRepos;
import grids.repos.UserRepos;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TweetService {
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private TweetRepos tweetRepos;
	@Autowired
	private TagRepos tagRepos;
	
	@Transactional(readOnly=true)
	public Tweet get(long tweetId) {
		return tweetRepos.get(tweetId);
	}
	
	public long tweet(long userId, String content, long[] tagIds) {
		Tweet t = new Tweet(content, userRepos.get(userId), new Date(), tagRepos.getTags(tagIds));
		tweetRepos.save(t);
		return t.getId();
	}
	
	public void comment(long originId) {
		//XXX
	}
	
	public List<?> comments(Tweet source) {
		//XXX
		return null;
	}
	
	public void share() {
		//XXX
	}
	
	public void reshare(Tweet origin) {
		//XXX
	}
	
	public void delete(Tweet t) {
		//XXX
	}
}
