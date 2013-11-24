package sage.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sage.entity.Follow;
import sage.entity.Tag;
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
    private List<Long> followedTagIds = new ArrayList<>();
    
    public UserCard(User user, int followingCount, int followerCount,
            Follow followFromCurrentUser, Follow followToCurrentUser, Collection<TagLabel> tags) {
        id = user.getId();
        name = user.getName();
        avatar = user.getAvatar();
        intro = user.getIntro();
        this.followingCount = followingCount;
        this.followerCount = followerCount;
        this.tags.addAll(tags);
        isFollowing = followFromCurrentUser != null;
        isFollower = followToCurrentUser != null;
        if (isFollowing) {
            for (Tag tag : followFromCurrentUser.getTags()) {
                followedTagIds.add(tag.getId());
            }
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
    
    public List<Long> getFollowedTagIds() {
        return followedTagIds;
    }
}
