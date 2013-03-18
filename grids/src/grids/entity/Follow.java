package grids.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="Follow")
public class Follow {
	private long id;
	private User source;
	private User target;
	private Set<Tag> tags = new HashSet<>();
	
	public Follow() {
	}
	
	public Follow(User source, User target, Set<Tag> tags) {
		this.source = source;
		this.target = target;
		this.tags = tags;
	}

	@Id
	@GeneratedValue
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	@ManyToOne(optional=false)
	public User getSource() {return source;}
	public void setSource(User source) {this.source = source;}
	
	@ManyToOne(optional=false)
	public User getTarget() {return target;}
	public void setTarget(User target) {this.target = target;}
	
	@ManyToMany
	public Set<Tag> getTags() {return tags;}
	public void setTags(Set<Tag> tags) {this.tags = tags;}
	
	@Override
	public String toString() {
		return source + "->" + target + tags;
	}
}
