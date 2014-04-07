package sage.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import sage.domain.repository.FollowRepository;
import sage.domain.repository.TagRepository;
import sage.domain.repository.UserRepository;
import sage.entity.Follow;
import sage.entity.User;
import sage.transfer.UserLabel;

@Service
@Transactional
public class RelationService {
  private Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  UserRepository userRepo;
  @Autowired
  FollowRepository followRepo;
  @Autowired
  TagRepository tagRepo;

  public void follow(long userId, long targetId, Collection<Long> tagIds) {
    if (userId == targetId) {
      logger.warn("user {} should not follow himself!", userId);
      return;
    }
    Assert.isNull(followRepo.find(userId, targetId));
    Follow follow = new Follow(
        userRepo.load(userId), userRepo.load(targetId),
        tagRepo.byIds(tagIds));
    followRepo.save(follow);
  }

  /*
   * May optimize it by findByFollowId (surrogate key)
   */
  public void editFollow(long userId, long targetId, Collection<Long> tagIds) {
    Follow follow = followRepo.find(userId, targetId);
    Assert.notNull(follow);
    follow.setTags(tagRepo.byIds(tagIds));
    followRepo.merge(follow);
  }

  public void unfollow(long userId, long targetId) {
    Follow follow = followRepo.find(userId, targetId);
    Assert.notNull(follow);
    followRepo.delete(follow);
  }

  @Transactional(readOnly = true)
  public Follow getFollow(long sourceId, long targetId) {
    return followRepo.find(sourceId, targetId);
  }

  @Transactional(readOnly = true)
  public Collection<Follow> followings(long userId) {
    return followRepo.followings(userId);
  }

  @Transactional(readOnly = true)
  public Collection<Follow> followers(long userId) {
    return followRepo.followers(userId);
  }

  @Transactional(readOnly = true)
  public Collection<UserLabel> friends(long userId) {
    List<Follow> followings = new ArrayList<Follow>(followings(userId));
    List<User> followingUsers = new ArrayList<>();
    for (Follow f : followings) {
      followingUsers.add(f.getTarget());
    }

    List<Follow> followers = new ArrayList<>(followers(userId));
    List<User> followerUsers = new ArrayList<>();
    for (Follow f : followers) {
      followerUsers.add(f.getSource());
    }

    followingUsers.retainAll(followerUsers);
    List<User> friendUsers = followingUsers;

    List<UserLabel> friends = new ArrayList<>();
    for (User u : friendUsers) {
      friends.add(new UserLabel(u));
    }
    return friends;
  }
}
