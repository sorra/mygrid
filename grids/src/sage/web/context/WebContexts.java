package sage.web.context;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class WebContexts {
    
    public static RequestAttributes current() {
        return RequestContextHolder.currentRequestAttributes();
    }
    
    public static Object getRequestBean(String name) {
        return current().getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
    
    public static void setRequestBean(String name, Object value) {
        current().setAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
    }
    
    public static Object getSessionBean(String name) {
        return current().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }
    
    public static void setSessionBean(String name, Object value) {
        current().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }
}