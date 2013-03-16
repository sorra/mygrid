package grids.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Tweet {
	private long id;
	private String content;
	private User author;
	private Date date;
	private List<Tag> tags;

	public Tweet() {}

	public Tweet(String content, User author, Date date, List<Tag> tags) {
		this.content = content;
		this.author = author;
		this.date = date;
		this.tags = tags;
	}

	@Id
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}

	@OneToOne
	public User getAuthor() {return author;}
	public void setAuthor(User author) {this.author = author;}

	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}

	@OneToMany(fetch=FetchType.EAGER)
	public List<Tag> getTags() {return tags;}
	public void setTags(List<Tag> tags) {this.tags = tags;}
}
