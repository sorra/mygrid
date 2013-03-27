package grids.transfer;

import grids.entity.User;

public class UserCard {
	private long id;
	private String name;
	private String avatar;
	private String intro;
	private int followingCount;
	private int followerCount;
	
	public UserCard(User user, int followingCount, int followerCount) {
		id = user.getId();
		name = user.getName();
		avatar = user.getAvatar();
		intro = user.getIntro();
		this.followingCount = followingCount;
		this.followerCount = followerCount;
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
	
}
