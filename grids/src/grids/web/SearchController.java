package grids.web;

import java.util.ArrayList;
import java.util.List;

import grids.search.SearchBase;
import httl.internal.util.StringUtils;

import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SearchBase searchBase;
	
	@RequestMapping("/search")
	public String search(
			@RequestParam("q") String q,
			ModelMap model) {
		logger.info("query: " + q);
		SearchHit[] hits = searchBase.search(q).getHits().getHits();
		
		List<String> jsons = new ArrayList<>();
		for (SearchHit hit : hits) {
			logger.info("~hit~ id:{} type:{}", hit.id(), hit.type());
			jsons.add(StringUtils.escapeXml(hit.sourceAsString()));
		}
		model.addAttribute("hits", jsons);
		return "search-result.httl";
	}
}
