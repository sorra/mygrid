package grids.transfer;

import grids.entity.User;

public class UserCard {
	private Long id;
	private String name;
	private String intro;
	
	public UserCard(User user) {
		id = user.getId();
		name = user.getName();
		intro = user.getIntro();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
}
