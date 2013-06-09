package sage.transfer;

import java.util.Date;

import sage.entity.Comment;

public class CommentCard {
	private long id;
	private String content;
	private long authorId;
	private String authorName;
	private String avatar;
	private Date time;
	
	public CommentCard(Comment comment) {
		id = comment.getId();
		content = comment.getContent();
		authorId = comment.getAuthor().getId();
		authorName = comment.getAuthor().getName();
		avatar = comment.getAuthor().getAvatar();
		time = comment.getTime();
	}
	
	public long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public long getAuthorId() {
		return authorId;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public Date getTime() {
		return time;
	}
}
