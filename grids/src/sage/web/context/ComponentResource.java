package sage.web.context;

public class ComponentResource {
    public static String includeCSS() {
        String[] components = (String[]) WebContexts.getRequestBean("components");
        return ComponentResourceManager.instance().includeCSS(components);
    }
    
    public static String includeJS() {
        String[] components = (String[]) WebContexts.getRequestBean("components");
        return ComponentResourceManager.instance().includeJS(components);
    }
}
