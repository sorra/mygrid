package sage.transfer;

import java.util.Collection;

import sage.entity.User;

public class UserSelf {
	private long id;
	private String name;
	private String avatar;
	private String intro;
	private int followingCount;
	private int followerCount;
	private Collection<TagLabel> topTags;
	
	public UserSelf(User user, int followingCount, int followerCount, Collection<TagLabel> topTags) {
		id = user.getId();
		name = user.getName();
		avatar = user.getAvatar();
		intro = user.getIntro();
		this.followingCount = followingCount;
		this.followerCount = followerCount;
		this.topTags = topTags;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getAvatar() {
		return avatar;
	}

	public String getIntro() {
		return intro;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public int getFollowerCount() {
		return followerCount;
	}
	
	public Collection<TagLabel> getTopTags() {
		return topTags;
	}
}
