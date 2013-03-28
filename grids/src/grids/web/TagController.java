package grids.web;

import grids.service.TagService;
import grids.transfer.TagCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tag")
public class TagController {
	@Autowired
	TagService tagService;
	
	@RequestMapping("/card/{id}")
	public TagCard tagCard(@PathVariable long id) {
		return tagService.tagCard(id);
	}
}
