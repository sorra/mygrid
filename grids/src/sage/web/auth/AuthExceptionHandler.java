package sage.web.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(RequireLoginException.class)
    public String redirectLogin(HttpServletRequest request) {
        return "redirect:/login?" + AuthUtil.getRedirectGoto(request.getRequestURI());
    }
}
