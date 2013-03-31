package grids.web;

import grids.service.StreamService;
import grids.transfer.Stream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/read")
public class ReadController {
	@Autowired
	private StreamService streamService;
	
	@RequestMapping("/istream")
	@ResponseBody
	public Stream istream(HttpSession session) {
		Long uid = (Long) session.getAttribute(SessionKeys.UID);
		if (uid == null) {
			System.out.println("uid is null, so return null stream.");
			return null;
		}
		return streamService.istream(uid);
	}
}
