package sage.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sage.domain.Comparators;
import sage.domain.repository.FavRepository;
import sage.domain.repository.UserRepository;
import sage.entity.Fav;

@Service
@Transactional
public class FavService {
  @Autowired
  private FavRepository favRepo;
  @Autowired
  private UserRepository userRepo;
  
  @Transactional(readOnly=true)
  public Collection<Fav> favs(long userId) {
    List<Fav> favs = new ArrayList<>(favs(userId));
    Collections.sort(favs, Comparators.favOnId);
    return favs;
  }
  
  public void addFav(String link, long ownerId) {
    Fav fav = new Fav(link, userRepo.load(ownerId), new Date());
    favRepo.save(fav);
  }
}
