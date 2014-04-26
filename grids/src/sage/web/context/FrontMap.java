package sage.web.context;

import org.springframework.ui.ModelMap;

@SuppressWarnings("serial")
public class FrontMap extends ModelMap {
  public static final String NAME = "frontMap";
  
  public static FrontMap from(ModelMap model) {
    FrontMap fm = new FrontMap();
    model.addAttribute(NAME, fm);
    return fm;
  }
  
  @Override
  public String toString() {
    return JsonUtil.json(this);
  }
}
