package grids.entity;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class Follow {
	private User follower;
	private User target;
	private List<Tag> tags;
	
	public User getFollower() {
		return follower;
	}
	public void setFollower(User follower) {
		this.follower = follower;
	}
	public User getTarget() {
		return target;
	}
	public void setTarget(User target) {
		this.target = target;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
}
