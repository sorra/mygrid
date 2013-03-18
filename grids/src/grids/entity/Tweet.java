package grids.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity(name="Tweet")
public class Tweet {
	private long id;
	private String content;
	private User author;
	private Date date;
	private Set<Tag> tags = new HashSet<>();

	public Tweet() {
	}

	public Tweet(String content, User author, Date date, Set<Tag> tags) {
		this.content = content;
		this.author = author;
		this.date = date;
		this.tags = tags;
	}

	@Id
	@GeneratedValue
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}

	@OneToOne
	public User getAuthor() {return author;}
	public void setAuthor(User author) {this.author = author;}

	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}

	@ManyToMany(fetch=FetchType.EAGER)
	public Set<Tag> getTags() {return tags;}
	public void setTags(Set<Tag> tags) {this.tags = tags;}
	
	@Override
	public String toString() {
		return author + ": " + content + tags;
	}
}
