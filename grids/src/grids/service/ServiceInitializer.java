package grids.service;

import java.io.PrintStream;

import grids.entity.Tag;
import grids.entity.User;
import grids.transfer.Item;
import grids.transfer.Stream;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ServiceInitializer {
	PrintStream out = System.out;
	@Autowired
	TagService tagService;
	@Autowired
	UserService userService;
	@Autowired
	RelationService relationService;
	@Autowired
	TweetService tweetService;
	@Autowired
	BlogService blogService;
	@Autowired
	StreamService streamService;
	@Autowired
	PageService pageService;
	
	@PostConstruct
	public void init() {
		tagService.init();
		
		tag();
		user();
		relation();
		post();
		istream();
	}
	
	long root = Tag.ROOT_ID;
	long society, culture, economy, tech;
	long view, art, painting, music;	
	long admin, bethia, centos;

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
		admin = userService.register(
				new User("admin@", "123", "Admin", "伟大的Admin"));
		bethia = userService.register(
				new User("bethia@", "123", "Bethia", "Elegant user"));
		centos = userService.register(
				new User("centos@", "123", "CentOS社区", "CentOS Fans Community"));
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
				new long[]{1, 2}, true);
		blogService.blog(bethia, "Beta",
				"Gbdsvkbnklr[\nsalspsp\nLas\n32",
				new long[]{1, 2}, true);
		blogService.blog(centos, "Gamma",
				"GVIWSWOWdv*&[\n(@)\tpsp\nT^HJ",
				new long[]{1, 2}, true);
		long a1 = tweetService.tweet(admin, "Post at root.", new long[]{root}).getId();
		long a2 = tweetService.tweet(admin, "HUUSF View age.", new long[]{view}).getId();
		long b1 = tweetService.tweet(bethia, "Music better!", new long[]{music}).getId();
		long c1 = tweetService.tweet(centos, "Tech status", new long[]{tech}).getId();
		
		tweetService.forward(admin, "forward", a1);
		tweetService.forward(bethia, "OK, good", a2);
	}
	
	private void istream() {
		printStream(streamService.istream(admin));
		printStream(streamService.istream(bethia));
		printStream(streamService.istream(centos));
	}
	
	private void printStream(Stream st) {
		out.println(st.toString());
		for (Item t: st.getItems()) {
			out.println(t);
		}
		out.println();
	}
}
