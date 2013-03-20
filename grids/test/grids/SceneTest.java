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
	
	long root = Tag.ROOT_ID;
	long society;
	long culture;
	long economy;
	long tech;
	
	long view;
	long art;
	long painting;
	long music;
	
	long admin;
	long bethia;
	long centos;
	
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
		society = tagService.newTag("社会", root);
		culture = tagService.newTag("文化", root);
		economy = tagService.newTag("经济", root);
		tech = tagService.newTag("科技", root);
		
		view = tagService.newTag("观察", society);
		art = tagService.newTag("艺术", culture);
		painting = tagService.newTag("绘画", art);
		music = tagService.newTag("音乐", art);
	}
	
	private void user() {
		admin = userService.register(new User("Admin", "123", "伟大的Admin"));
		bethia = userService.register(new User("Bethia", "", "Elegant user"));
		centos = userService.register(new User("CentOS社区", "123", "CentOS Fans Community"));
	}
	
	private void relation() {
		relationService.follow(1, 2, new long[]{society, culture});
		relationService.follow(1, 3, new long[]{society, culture});
		
		relationService.follow(2, 1, new long[]{music, view});
		relationService.follow(2, 3, new long[]{music});
		
		relationService.follow(3, 1, new long[]{tech, view});
		relationService.follow(3, 2, new long[]{art});
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
		tweetService.tweet(1, "Post at root.", new long[]{root});
		tweetService.tweet(1, "HUUSF View age.", new long[]{view});
		tweetService.tweet(2, "Music better!", new long[]{music});
		tweetService.tweet(3, "Tech status", new long[]{tech});
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
