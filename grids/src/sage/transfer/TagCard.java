package sage.transfer;

import java.util.ArrayList;
import java.util.List;

import sage.entity.Tag;

public class TagCard {
  private long id;
  private String name;
  private List<TagLabel> chainUp = new ArrayList<>();

  public TagCard(Tag tag) {
    id = tag.getId();
    name = tag.getName();
    for (Tag node : tag.chainUp()) {
      chainUp.add(new TagLabel(node));
    }
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * @see Tag#chainUp()
   */
  public List<TagLabel> getChainUp() {
    return chainUp;
  }

  @Override
  public String toString() {
    if (chainUp.isEmpty()) {
      return name;
    }
    else {
      StringBuilder sb = new StringBuilder();
      for (int i = chainUp.size() - 1; i >= 0; i--) {
        sb.append(chainUp.get(i));
        if (i > 0)
          sb.append("->");
      }
      return sb.toString();
    }
  }
}
