package sage.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sage.domain.repository.CommentRepository;
import sage.domain.repository.TweetRepository;
import sage.domain.repository.UserRepository;
import sage.entity.Blog;
import sage.entity.Tweet;
import sage.transfer.BlogData;
import sage.transfer.TweetCard;

@Service
@Transactional(readOnly=true)
public class TransferService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TweetRepository tweetRepo;
    @Autowired
    private CommentRepository commentRepo;
    
    public BlogData getBlogData(Blog blog) {
        long authorId = blog.getAuthor().getId();
        return new BlogData(blog, userRepo.get(authorId));
    }

    public TweetCard getTweetCard(Tweet tweet) {
        return new TweetCard(tweet,
                forwardCount(tweet.getId()),
                commentCount(tweet.getId()));
    }
    
    public TweetCard getTweetCardNoCount(Tweet tweet) {
        return new TweetCard(tweet, 0, 0);
    }

    public long forwardCount(long originId) {
        return tweetRepo.forwardCount(originId);
    }

    public long commentCount(long sourceId) {
        return commentRepo.commentCount(sourceId);
    }
}
