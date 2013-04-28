package grids.transfer;

import grids.entity.Blog;

import java.util.Date;
import java.util.List;

public class BlogCard implements Item {
	private String type = "BlogCard";
	
	private long id;
//	private UserInfo author;
	private String content;
	private Date time;
	private List<TagLabel> tags;
	
	
	public BlogCard(Blog blog) {
	}
	
	public long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getTime() {
		return time;
	}

	@Override
	public List<TagLabel> getTags() {
		return tags;
	}

	@Override
	public String getType() {
		return type;
	}

}
