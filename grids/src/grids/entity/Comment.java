package grids.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="Comment")
public class Comment {
	private long id;
	private String content;
	private User author;
	private Date date;
	private Tweet source;
	
	public Comment() {
	}
	
	public Comment(String content, User author, Date date, Tweet source) {
		this.content = content;
		this.author = author;
		this.date = date;
		this.source = source;
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
	
	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}
	
	@ManyToOne
	public Tweet getSource() {return source;}
	public void setSource(Tweet source) {this.source = source;}
	
	@Override
	public String toString() {
		return author + ": " + content;
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
