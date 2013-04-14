package grids.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="Tweet")
public class Tweet {
	private long id;
	private String content;
	private User author;
	private Date time;
	private Tweet origin = null; 
	private Set<Tag> tags = new HashSet<>();
	private Collection<Comment> comments = new ArrayList<>();

	public Tweet() {
	}

	public Tweet(String content, User author, Date time, Set<Tag> tags) {
		this.content = content;
		this.author = author;
		this.time = time;
		this.tags = tags;
	}
	
	public Tweet(String content, User author, Date time, Tweet origin) {
		this.content = content;
		this.author = author;
		this.time = time;
		this.origin = origin;
		if (origin.getOrigin() != null) {//XXX need better approach
			throw new IllegalArgumentException("tweet's origin should not be nested!");
		}
	}

	@Id
	@GeneratedValue
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}

	@ManyToOne(optional=false)
	public User getAuthor() {return author;}
	public void setAuthor(User author) {this.author = author;}

	public Date getTime() {return time;}
	public void setTime(Date time) {this.time = time;}

	@OneToOne
	public Tweet getOrigin() {return origin;}
	public void setOrigin(Tweet origin) {this.origin = origin;}

	@ManyToMany(fetch=FetchType.EAGER)
	public Set<Tag> getTags() {return tags;}
	public void setTags(Set<Tag> tags) {this.tags = tags;}
	
	@OneToMany(mappedBy="source")
	public Collection<Comment> getComments() {return comments;}
	public void setComments(Collection<Comment> comments) {this.comments = comments;}
	
	@Override
	public String toString() {
		return author + ": " + content + tags;
	}
}
