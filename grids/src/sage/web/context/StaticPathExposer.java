package sage.web.context;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaticPathExposer {
    public static final String BASE = "/grids";
    public static final String RS = "/grids/rs";
    
    @Autowired
	private ServletContext servletContext;
	
	@PostConstruct
	public void init() {
		servletContext.setAttribute("base", BASE);
		servletContext.setAttribute("rs", RS);
	}
	
}
