package sage.transfer;

import java.util.ArrayList;
import java.util.List;

import sage.entity.User;

public class UserCard {
	private long id;
	private String name;
	private String avatar;
	private String intro;
	private int followingCount;
	private int followerCount;
	private boolean isFollowing;
	private boolean isFollower;
	private List<TagLabel> tags = new ArrayList<>();
	
	public UserCard(User user, int followingCount, int followerCount,
			boolean isFollowing, boolean isFollower, List<TagLabel> tags) {
		id = user.getId();
		name = user.getName();
		avatar = user.getAvatar();
		intro = user.getIntro();
		this.followingCount = followingCount;
		this.followerCount = followerCount;
		this.isFollowing = isFollowing;
		this.isFollower = isFollower;
		for (TagLabel tag : tags) {
			this.tags.add(tag);
		}
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
	
	public boolean getIsFollowing() {
		return isFollowing;
	}
	
	public boolean getIsFollower() {
		return isFollower;
	}
	
	public List<TagLabel> getTags() {
		return tags;
	}
}
