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
	PageService gridService;
	
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
		gridService = ac.getBean(PageService.class);
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
		bethia = userService.register(new User("Bethia", "123", "Elegant user"));
		centos = userService.register(new User("CentOS社区", "123", "CentOS Fans Community"));
	}
	
	private void relation() {
		relationService.follow(admin, bethia, new long[]{society, culture});
		relationService.follow(admin, centos, new long[]{society, culture});
		
		relationService.follow(bethia, admin, new long[]{music, view});
		relationService.follow(bethia, centos, new long[]{music});
		
		relationService.follow(centos, admin, new long[]{tech, view});
		relationService.follow(centos, bethia, new long[]{art});
	}
	
	private void post() {
		blogService.blog(admin, "Alpha",
				"Manfspaspdvopdopdsvsa[\nsalspsp\nLaspewogvs",
				new long[]{1, 2});
		blogService.blog(bethia, "Beta",
				"Gbdsvkbnklr[\nsalspsp\nLas\n32",
				new long[]{1, 2});
		blogService.blog(centos, "Gamma",
				"GVIWSWOWdv*&[\n(@)\tpsp\nT^HJ",
				new long[]{1, 2});
		tweetService.tweet(admin, "Post at root.", new long[]{root});
		tweetService.tweet(admin, "HUUSF View age.", new long[]{view});
		tweetService.tweet(bethia, "Music better!", new long[]{music});
		tweetService.tweet(centos, "Tech status", new long[]{tech});
	}
	
	private void istream() {
		printStream(streamService.istream(admin));
		printStream(streamService.istream(bethia));
		printStream(streamService.istream(centos));
	}
	
	private void printStream(Stream st) {
		out.println(st.toString());
		for (Tweet t: st.getTweets()) {
			out.println(t);
		}
		out.println();
	}
}
