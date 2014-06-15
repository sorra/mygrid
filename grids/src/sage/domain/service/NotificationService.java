package sage.domain.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sage.domain.repository.nosql.NotifRepository;
import sage.entity.nosql.Notif;
import sage.entity.nosql.Notif.Type;

@Service
public class NotificationService {

  @Autowired
  private NotifRepository notifRepo;
  
  public List<Notif> getNotifs(Long userId) {
    
  }
  
  public void forwarded(Long userToNotify, Long sourceId) {
    sendNotif(new Notif(userToNotify, Type.FORWARDED, sourceId));
  }
  
  public void commented(Long userToNotify, Long sourceId) {
    sendNotif(new Notif(userToNotify, Type.COMMENTED, sourceId));
  }
  
  public void mentionedByTweet(Long userToNotify, Long sourceId) {
    sendNotif(new Notif(userToNotify, Type.MENTIONED_TWEET, sourceId));
  }
  
  public void mentionedByComment(Long userToNotify, Long sourceId) {
    sendNotif(new Notif(userToNotify, Type.MENTIONED_COMMENT, sourceId));
  }
  
  private Boolean sendNotif(Notif notif) {
    long time = System.currentTimeMillis();
    String id = generateId(notif, time);
    try {
      Boolean success = notifRepo.add(id, notif).get();
      if (success) {
        return success;
      } else {
        // Retry once
        id = generateId(notif, time+1);
        return notifRepo.add(id, notif).get();
      }
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
  
  private String generateId(Notif notif, long time) {
    String id = notif.getOwnerId() + "_" + Long.toHexString(time);
    notif.setId(id);
    return id;
  }
}
