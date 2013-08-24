package sage.web.context;

public class ComponentResource {
    public static String includeCSS(String[] components) {
        return ComponentResourceManager.instance().includeCSS(components);
    }
    
    public static String includeJS(String[] components) {
        return ComponentResourceManager.instance().includeJS(components);
    }
}
