package sage.transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sage.entity.Blog;
import sage.entity.Tag;
import sage.entity.User;

public class BlogData {
    
    private long id;
    private long authorId;
    private String authorName;
    private String avatar;
    private UserCard authorCard = null;
    private String title;
    private String content;
    private Date time;
    private List<TagLabel> tags;
    
    public BlogData(Blog blog, User author) {
        id = blog.getId();
        authorId = author.getId();
        authorName = author.getName();
        avatar = author.getAvatar();
        title = blog.getTitle();
        content = blog.getContent();
        time = blog.getTime();
        tags = new ArrayList<>();
        for (Tag tag : blog.getTags()) {
            tags.add(new TagLabel(tag));
        }
    }
    
    public long getId() {
        return id;
    }
    
    public long getAuthorId() {
        return authorId;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public UserCard getAuthorCard() {
        return authorCard;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getContent() {
        return content;
    }
    
    public Date getTime() {
        return time;
    }

    public List<TagLabel> getTags() {
        return tags;
    }
}
