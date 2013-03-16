package grids.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Follow {
	private long id;
	private User source;
	private User target;
	private List<Tag> tags;
	
	public Follow() {
	}
	
	public Follow(User source, User target, List<Tag> tags) {
		this.source = source;
		this.target = target;
		this.tags = tags;
	}

	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(nullable=false)
	public User getSource() {
		return source;
	}
	public void setSource(User source) {
		this.source = source;
	}
	
	@ManyToOne
	@JoinColumn(nullable=false)
	public User getTarget() {
		return target;
	}
	public void setTarget(User target) {
		this.target = target;
	}
	
	@OneToMany
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
}
