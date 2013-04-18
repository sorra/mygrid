package grids.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthUtil {
	private final static Logger logger = LoggerFactory.getLogger(AuthUtil.class);
	
	/**
	 * 
	 * @param session HttpSession
	 * @return uid when logged in, null when not logged in
	 */
	public static Long checkLoginUid(HttpSession session) {
		Long uid = (Long) session.getAttribute(SessionKeys.UID);
		if (uid == null) {
			logger.info("Not logged in!");
		}
		return uid;
	}
}
