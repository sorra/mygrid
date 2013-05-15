package grids.web;

import grids.service.TagService;
import grids.transfer.TagCard;
import grids.transfer.TagNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tag")
public class TagController {
	@Autowired
	TagService tagService;
	
	@RequestMapping("/card/{id}")
	@ResponseBody
	public TagCard tagCard(@PathVariable long id) {
		return tagService.getTagCard(id);
	}
	
	@RequestMapping("/tree")
	@ResponseBody
	public TagNode tagTree() {
		return tagService.getTagTree();
	}
}
