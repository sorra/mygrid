package grids.transfer;

import grids.entity.Blog;
import grids.entity.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogData {
	
	private long id;
	private UserCard author;
	private String content;
	private Date time;
	private List<TagLabel> tags;
	
	public BlogData(Blog blog, UserCard author) {
		id = blog.getId();
		this.author = author;
		content = blog.getContent();
		time = blog.getTime();
		tags = new ArrayList<>();
		for (Tag tag : blog.getTags()) {
			tags.add(new TagLabel(tag));
		}
	}
	
	public long getId() {
		return id;
	}
	
	public UserCard getAuthor() {
		return author;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getTime() {
		return time;
	}

	public List<TagLabel> getTags() {
		return tags;
	}
}
