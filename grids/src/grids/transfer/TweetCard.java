package grids.transfer;

import grids.entity.Tag;
import grids.entity.Tweet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TweetCard {
	private long id;
	private long authorId;
	private String authorName;
	private String avatar;
	private String content;
	private Date date;
	private TweetCard origin = null;
	private List<TagCard> tags = new ArrayList<>();
	
	public TweetCard(Tweet tweet) {
		id = tweet.getId();
		authorId = tweet.getAuthor().getId();
		authorName = tweet.getAuthor().getName();
		avatar = tweet.getAuthor().getAvatar();
		content = tweet.getContent();
		date = tweet.getDate();
		if (tweet.getOrigin() != null) {
			origin = new TweetCard(tweet.getOrigin());
		}
		for (Tag tag : tweet.getTags()) {
			tags.add(new TagCard(tag));
		}
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

	public List<TagCard> getTags() {
		return tags;
	}
	
	@Override
	public String toString() {
		return authorName + ": " + content + tags;
	}
}
