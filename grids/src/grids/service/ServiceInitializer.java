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
	TweetPostService tweetPostService;
	@Autowired
	BlogPostService blogService;
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
		blogService.newBlog(admin, "浅谈面向对象语言的类型运算",
			"像C#或者Haskell这样的先进的语言都有一个跟语法分不开的最核心的库。"
			+ "譬如说C#的int，是mscorlib.dll里面的System.SInt32，Haskell的(x:xs)"
			+ "则定义在了prelude里面。Vczh Library++ 3.0的ManagedX语言也有一个"
			+ "类似mscorlib.dll的东西。之前的NativeX提供了一个核心的函数库叫"
			+ "System.CoreNative (syscrnat.assembly)，因此ManagedX的就命名为"
			+ "System.CoreManaged (syscrman.assembly)。System.CoreManaged里面"
			+ "的预定义对象都是一些基本的、不可缺少的类型，例如System.SInt32、"
			+ "System.IEnumerable<T>或者System.Reflection.Type。昨天晚上我的"
			+ "未完成的语义分析器的完成程度已经足以完全分析System.CoreManaged里面的"
			+ "托管代码了，因此符号表里面的类型系统也基本上是一个完整的类型系统。"
			+ "在开发的过程中得到的心得体会便是写着一篇文章的来源。如今，"
			+ "先进的面向对象语言的类型都离不开下面的几个特征：对象类型、函数类型和接口类型。"
			+ "修饰类型的工具则有泛型和延迟绑定等等。譬如说C#，对象类型便是object，"
			+ "函数类型则有.net framework支持的很好，但是不是核心类型的Func和Action，"
			+ "接口类型则类似IEnumerable。泛型大家都很熟悉，延迟绑定则类似于dynamic关键字。"
			+ "var关键字是编译期绑定的，因此不计算在内。Java的int是魔法类型，其设计的错误已经"
			+ "严重影响到了类库的优美程度，其使用“类型擦除”的泛型系统也为今后的发展留下了一些祸根，"
			+ "因此这些旁门左道本文章就不去详细讨论了。这篇文章讲针对重要的那三个类型和两个修饰"
			+ "进行讨论，并解释他们之间互相换算的方法。",
			new long[]{1, 2}, true);
		blogService.newBlog(bethia, "Beta",
			"Gbdsvkbnklr[\nsalspsp\nLas\n32",
			new long[]{1, 2}, true);
		blogService.newBlog(centos, "Gamma",
			"GVIWSWOWdv*&[\n(@)\tpsp\nT^HJ",
			new long[]{1, 2}, true);
		long a1 = tweetPostService.newTweet(admin, "Post at root.", new long[]{root}).getId();
		long a2 = tweetPostService.newTweet(admin, "HUUSF View age.", new long[]{view}).getId();
		long b1 = tweetPostService.newTweet(bethia, "Music better!", new long[]{music}).getId();
		long c1 = tweetPostService.newTweet(centos, "Tech status", new long[]{tech}).getId();
		
		tweetPostService.forward(admin, "forward", a1);
		tweetPostService.forward(bethia, "OK, good", a2);
		tweetPostService.forward(admin, "Oh, yeah", b1);
		tweetPostService.forward(admin, "See it!", c1);
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
