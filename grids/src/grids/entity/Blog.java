package grids.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="Blog")
public class Blog {
	private long id;
	private String title;
	private String content;
	private User author;
	private Date time;
	private Set<Tag> tags = new HashSet<>();

	public Blog() {
	}

	public Blog(String title, String content, User author, Date time, Set<Tag> tags) {
		this.title = title;
		this.content = content;
		this.author = author;
		this.time = time;
		this.tags = tags;
	}

	@Id
	@GeneratedValue
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}

	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}

	@ManyToOne(optional=false)
	public User getAuthor() {return author;}
	public void setAuthor(User author) {this.author = author;}

	public Date getTime() {return time;}
	public void setTime(Date time) {this.time = time;}

	@ManyToMany(fetch=FetchType.EAGER)
	public Set<Tag> getTags() {return tags;}
	public void setTags(Set<Tag> tags) {this.tags = tags;}
	
	@Override
	public String toString() {
		return author + ": " + title + tags
				+ "\n" + content;
	}
}
