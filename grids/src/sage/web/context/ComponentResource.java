package sage.web.context;

import java.util.Arrays;
import java.util.Collection;

public class ComponentResource {
    
    public static String includeCSS(Collection<String> components) {
        StringBuilder sb = new StringBuilder();
        for (String each : components) {
            sb.append(includeOneCSS(each)).append('\n');
        }
        return sb.toString();
    }
    
    public static String includeCSS(String[] components) {
        return includeCSS(Arrays.asList(components));
    }
    
    public static String includeJS(Collection<String> components) {
        StringBuilder sb = new StringBuilder(includeOneJS("jquery-1.9.1")).append('\n');
        for (String each : components) {
            sb.append(includeOneJS(each)).append('\n');
        }
        return sb.toString();
    }
    
    public static String includeJS(String[] components) {
        return includeJS(Arrays.asList(components));
    }
    
    static String includeOneCSS(String componentName) {
        return String.format("<link href=\"%s/css/%s.css\" rel=\"stylesheet\" "
                + "type=\"text/css\" media=\"screen\" />", StaticPathExposer.RS, componentName);
    }
    
    static String includeOneJS(String componentName) {
        return String.format("<script src=\"%s/js/%s.js\" "
                + "type=\"text/javascript\"></script>", StaticPathExposer.RS, componentName);
    }    
}
