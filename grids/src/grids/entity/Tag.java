package grids.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tag {
	@Id
	private long id;
	private String name;
	private List<Tag> children;
	private Tag parent;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Tag> getChildren() {
		return children;
	}
	public void setChildren(List<Tag> children) {
		this.children = children;
	}
	public Tag getParent() {
		return parent;
	}
	public void setParent(Tag parent) {
		this.parent = parent;
	}
}
