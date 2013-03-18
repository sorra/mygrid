package grids;

import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import grids.entity.*;
import grids.service.*;
import grids.transfer.Stream;

public class SceneTest {
	PrintStream out = System.out;
	
	UserService userService;
	RelationService relationService;
	TweetService tweetService;
	BlogService blogService;
	TagService tagService;
	StreamService streamService;
	GridService gridService;
	
	
	@Before
	public void setUp() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("root-context.xml");
		userService = ac.getBean(UserService.class);
		relationService = ac.getBean(RelationService.class);
		tweetService = ac.getBean(TweetService.class);
		blogService = ac.getBean(BlogService.class);
		tagService = ac.getBean(TagService.class);
		streamService = ac.getBean(StreamService.class);
		gridService = ac.getBean(GridService.class);
		
//		tagService.init();
	}
	
	@Test
	public void procedure() {
		tag();
		user();
		relation();
		post();
		istream();
	}
	
	private void tag() {
		tagService.newTag("社会", 1);
		tagService.newTag("文化", 1);
		tagService.newTag("科技", 1);
	}
	
	private void user() {
		userService.register(new User("Admin", "123", "伟大的Admin"));
		userService.register(new User("Bethia", "", "Elegant user"));
		userService.register(new User("CentOS社区", "123", "CentOS Fans Community"));
	}
	
	private void relation() {
		relationService.follow(1, 2, new long[]{2, 3});
		relationService.follow(1, 3, new long[]{2, 3});
		relationService.follow(2, 1, new long[]{2});
		relationService.follow(3, 1, new long[]{2});
		relationService.follow(2, 3, new long[]{2});
		relationService.follow(3, 2, new long[]{2});
	}
	
	private void post() {
		blogService.blog(1, "Alpha",
				"Manfspaspdvopdopdsvsa[\nsalspsp\nLaspewogvs",
				new long[]{1, 2});
		blogService.blog(2, "Beta",
				"Gbdsvkbnklr[\nsalspsp\nLas\n32",
				new long[]{1, 2});
		blogService.blog(3, "Gamma",
				"GVIWSWOWdv*&[\n(@)\tpsp\nT^HJ",
				new long[]{1, 2});
		tweetService.tweet(1, "Post at root.", new long[]{1});
		tweetService.tweet(1, "HUUSF", new long[]{2, 3});
		tweetService.tweet(2, "Better *Wahaha!", new long[]{2});
		tweetService.tweet(3, "Seen less gebdpro", new long[]{3});
	}
	
	private void istream() {
		printStream(streamService.istream(1));
		printStream(streamService.istream(2));
		printStream(streamService.istream(3));
	}
	
	private void printStream(Stream st) {
		out.println(st.toString());
		for (Tweet t: st.getTweets()) {
			out.println(t);
		}
		out.println();
	}
}
