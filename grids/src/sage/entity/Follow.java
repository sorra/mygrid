package sage.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "Follow")
public class Follow {
  private Long id;
  private User source;
  private User target;
  private Set<Tag> tags = new HashSet<>();

  public Follow() {
  }

  public Follow(User source, User target, Set<Tag> tags) {
    if (source.getId() == target.getId()) {
      throw new IllegalArgumentException("source should not equal to target!");
    }
    this.source = source;
    this.target = target;
    this.tags.addAll(tags);
  }

  @Id
  @GeneratedValue
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  @ManyToOne(optional = false)
  public User getSource() {
    return source;
  }
  public void setSource(User source) {
    this.source = source;
  }

  @ManyToOne(optional = false)
  public User getTarget() {
    return target;
  }
  public void setTarget(User target) {
    this.target = target;
  }

  @ManyToMany
  public Set<Tag> getTags() {
    return tags;
  }
  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return source + "->" + target + tags;
  }

  @Override
  public int hashCode() {
    return Long.valueOf(id).hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Follow other = (Follow) obj;
    if (id != other.id)
      return false;
    return true;
  }
}
