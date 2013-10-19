package sage.domain.service;

import httl.util.StringUtils;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sage.domain.Constants;
import sage.domain.repository.CommentRepository;
import sage.domain.repository.TagRepository;
import sage.domain.repository.TweetRepository;
import sage.domain.repository.UserRepository;
import sage.domain.search.SearchBase;
import sage.entity.Blog;
import sage.entity.Comment;
import sage.entity.Tweet;
import sage.entity.User;
import sage.transfer.TweetCard;

@Service
@Transactional
public class TweetPostService {
	@Autowired
	private SearchBase searchBase;
	@Autowired
	private TransferService transferService;
	@Autowired
	private UserRepository userRepos;
	@Autowired
	private TweetRepository tweetRepos;
	@Autowired
	private TagRepository tagRepos;
	@Autowired
	private CommentRepository commentRepos;
	
	public Tweet newTweet(long userId, String content, Collection<Long> tagIds) {
        content = processContent(content);
		Tweet tweet = new Tweet(content, userRepos.load(userId), new Date(),
				tagRepos.byIds(tagIds));
		tweetRepos.save(tweet);
		searchBase.index(tweet.getId(), transferService.getTweetCardNoCount(tweet));
		return tweet;
	}
	
	public Tweet forward(long userId, String content, long originId) {
        content = processContent(content);
        Tweet origin = tweetRepos.load(originId);
    	Tweet tweet = new Tweet(content,
    	        userRepos.load(userId), new Date(), pureOrigin(origin));
    	tweetRepos.save(tweet);
    	searchBase.index(tweet.getId(), transferService.getTweetCardNoCount(tweet));
    	return tweet;
    }

    public void comment(long userId, String content, long sourceId) {
        content = processContent(content);
		Comment comment = new Comment(content, userRepos.load(userId),
				new Date(), tweetRepos.load(sourceId));
		commentRepos.save(comment);
	}
	
	public void share(long userId, String content, String sourceUrl) {
		//XXX
	}
	
	public void share(long userId, Blog blog) {
		final int SUM_LEN = 100;
		String content = blog.getContent();
		String summary = content.length()>SUM_LEN ? content.substring(0, SUM_LEN) : content;
		Tweet tweet = new Tweet(
				"发表了博客：["+blogRef(blog)+"] "+summary,
				userRepos.load(userId),
				new Date(),
				blog);
		tweetRepos.save(tweet);
		searchBase.index(tweet.getId(), transferService.getTweetCardNoCount(tweet));
	}
	
	public boolean delete(long userId, long tweetId) {
		Tweet tweet = tweetRepos.load(tweetId);
		if (userId == tweet.getAuthor().getId()) {
			tweetRepos.delete(tweet);
			searchBase.delete(TweetCard.class, tweetId);
			return true;
		}
		else return false;
	}
	
	private String blogRef(Blog blog) {
		return String.format("<a href=\"%s\">%s</a>",
				Constants.WEB_CONTEXT_ROOT + "/blog/" + blog.getId(), blog.getTitle());
	}
	
	private Tweet pureOrigin(Tweet tweet) {
	    if (tweet.getOrigin() == null) {
	        return tweet;
	    }
	    else {
	        return pureOrigin(tweet.getOrigin());
        }
	}
	
	private String processContent(String content) {
	    content = StringUtils.escapeXml(content);
	    return replaceMention(content, 0, new StringBuilder(), userRepos);
	}

	public static String replaceMention(String content, int startIndex, StringBuilder sb, UserRepository ur) {
	    int indexOfAt = content.indexOf('@', startIndex);
	    int indexOfSpace = content.indexOf(' ', indexOfAt);
	    int indexOfInnerAt = content.lastIndexOf('@', indexOfSpace-1);
	    System.out.println(indexOfAt+" "+indexOfSpace+" "+indexOfInnerAt);
	    
        if (indexOfAt >= 0 && indexOfSpace >= 0) {
            if (indexOfInnerAt > indexOfAt && indexOfInnerAt < indexOfSpace) {
                indexOfAt = indexOfInnerAt;
            }
            String name = content.substring(indexOfAt+1, indexOfSpace);
            User user = ur.findByName(name);
            System.out.println(user);
            if (user != null) {
                sb.append(content.substring(startIndex, indexOfAt)).append('@').append(name)
                        .append('#').append(user.getId());
                return replaceMention(content, indexOfSpace, sb, ur);
            }
            else {
                if (startIndex == 0) {
                    return content;
                }
                sb.append(content.substring(indexOfAt, indexOfSpace));
                return replaceMention(content, indexOfSpace, sb, ur);
            }
        }
	    
	    // Exit
	    if (startIndex == 0) {
	        return content;
	    }
	    return sb.append(content.substring(startIndex)).toString();
	}
}
