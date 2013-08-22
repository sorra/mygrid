package sage.web.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class StaticPathExposer implements ServletContextAware {
    public static final String BASE = "/grids";
    public static final String RS = "/grids/rs";
    
	private ServletContext servletContext;
	
	public void init() {
		servletContext.setAttribute("base", BASE);
		servletContext.setAttribute("rs", RS);
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
