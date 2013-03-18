package grids.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ServiceInitializer {
	@Autowired
	TagService tagService;
	
	@PostConstruct
	public void init() {
		tagService.init();
	}
}
