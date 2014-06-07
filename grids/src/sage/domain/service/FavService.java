package sage.domain.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sage.domain.repository.FavRepository;
import sage.domain.repository.UserRepository;
import sage.entity.Fav;

@Service
public class FavService {
  @Autowired
  private FavRepository favRepo;
  @Autowired
  private UserRepository userRepo;
  
  public Collection<Fav> favs(long userId) {
    return new ArrayList<>(favs(userId));
  }
  
  public void addFav(String link, long ownerId) {
    Fav fav = new Fav(link, userRepo.load(ownerId));
    favRepo.save(fav);
  }
}
