package sage.web.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

public class StaticPathExposer implements ServletContextAware {
	private ServletContext servletContext;
	
	public void init() {
		servletContext.setAttribute("base", "/grids");
		servletContext.setAttribute("rs", "/grids/rs");
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
