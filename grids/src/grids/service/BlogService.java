package grids.service;

import java.util.Collections;
import java.util.Date;

import grids.entity.Blog;
import grids.entity.Tag;
import grids.repos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlogService {
	@Autowired
	private BlogRepos blogRepos;
	@Autowired
	private UserRepos userRepos;
	@Autowired
	private TagRepos tagRepos;
	
	@Deprecated
	@Transactional(readOnly=true)
	public Blog get() {
		return new Blog("哎呦喂", "我擦嘞闹不住了菇，我擦嘞闹得住了菇。",
				userRepos.get(0), new Date(), Collections.<Tag>emptySet());
	}
	
	public void blog(long userId, String title, String content, long[] tagIds) {
		Blog blog = new Blog(title, content, userRepos.load(userId), new Date(), tagRepos.getTags(tagIds));
		blogRepos.save(blog);
	}
	
	public void edit(Blog blog) {
		
	}
	public void delete(Blog blog) {
		
	}
}
