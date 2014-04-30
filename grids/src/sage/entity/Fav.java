package sage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "Fav")
public class Fav {
  private Long id;
  private String link;
  private User owner;
  
  public Fav() {}
  
  public Fav(String link, User owner) {
    this.link = link;
    this.owner = owner;
  }
  
  @Id @GeneratedValue
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}
  
  public String getLink() {return link;}
  public void setLink(String link) {this.link = link;}
  
  @OneToOne
  public User getOwner() {return owner;}
  public void setOwner(User owner) {this.owner = owner;}
}
