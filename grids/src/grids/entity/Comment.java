package grids.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="Comment")
public class Comment {
	private long id;
	private String content;
	private User author;
	private Date date;
	
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
	
	@Override
	public String toString() {
		return author + ": " + content;
	}
}
