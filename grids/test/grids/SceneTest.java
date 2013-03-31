package grids;

import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import grids.entity.*;
import grids.service.*;
import grids.transfer.Stream;
import grids.transfer.TweetCard;

public class SceneTest {
	PrintStream out = System.out;
	
	UserService userService;
	RelationService relationService;
	TweetService tweetService;
	BlogService blogService;
	TagService tagService;
	StreamService streamService;
	PageService pageService;
	
	@Before
	public void setUp() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("root-context.xml");
		userService = ac.getBean(UserService.class);
		relationService = ac.getBean(RelationService.class);
		tweetService = ac.getBean(TweetService.class);
		blogService = ac.getBean(BlogService.class);
		tagService = ac.getBean(TagService.class);
		streamService = ac.getBean(StreamService.class);
		pageService = ac.getBean(PageService.class);
	}
	
	@Test
	public void procedure() {
		Stream s1 = streamService.istream(1);
		Stream s2 = streamService.istream(2);
		Stream s3 = streamService.istream(3);
	}
	
}
