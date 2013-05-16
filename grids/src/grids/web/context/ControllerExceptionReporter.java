package grids.web.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionReporter {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@ExceptionHandler
	public void logAny(Throwable e) throws Throwable {
		logger.error("controller error encountered");
		throw e;
	}
}
