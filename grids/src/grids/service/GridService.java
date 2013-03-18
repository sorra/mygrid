package grids.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class GridService {

	public Object privateGrid() {
		return null;
	}
	public Object publicGrid() {
		return null;
	}
	public Object groupGrid() {
		return null;
	}
}
