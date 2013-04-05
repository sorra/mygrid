package grids.transfer;

import grids.entity.Tag;
import grids.entity.Tweet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TweetCard implements Item {
	private long id;
	private long authorId;
	private String authorName;
	private String avatar;
	private String content;
	private Date date;
	private TweetCard origin = null;
	private List<TagLabel> tags = new ArrayList<>();
	private long forwardCount;
	private long commentCount;
	
	public TweetCard(Tweet tweet, long forwardCount, long commentCount) {
		id = tweet.getId();
		authorId = tweet.getAuthor().getId();
		authorName = tweet.getAuthor().getName();
		avatar = tweet.getAuthor().getAvatar();
		content = tweet.getContent();
		date = tweet.getDate();
		if (tweet.getOrigin() != null) {
			origin = new TweetCard(tweet.getOrigin() , 0, 0);
		}
		for (Tag tag : tweet.getTags()) {
			tags.add(new TagLabel(tag));
		}
		this.forwardCount = forwardCount;
		this.commentCount = commentCount;
	}
	
	/**
	 * used by CombineGroup
	 */
	public void clearOrigin() {
		origin = null;
	}

	public long getId() {
		return id;
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
	public String getContent() {
		return content;
	}
	public Date getDate() {
		return date;
	}
	public TweetCard getOrigin() {
		return origin;
	}
	@Override
	public List<TagLabel> getTags() {
		return tags;
	}
	public long getForwardCount() {
		return forwardCount;
	}
	public long getCommentCount() {
		return commentCount;
	}

	@Override
	public String toString() {
		return authorName + ": " + content + tags;
	}
}
