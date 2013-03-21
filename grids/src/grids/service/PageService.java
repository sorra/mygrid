package grids.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class PageService {

	public Object privatePage() {
		return null;
	}
	public Object publicPage() {
		return null;
	}
	public Object groupPage() {
		return null;
	}
}
