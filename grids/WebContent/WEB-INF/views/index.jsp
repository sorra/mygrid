<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>网格</title>
	<link href="rs/css/style.css" rel="stylesheet" type="text/css" media="screen" />
	<script src="rs/js/jquery.min.js" type="text/javascript"></script> 
	<script src="rs/js/animate-bg.js" type="text/javascript"></script>
	<script src="rs/js/scripts.js" type="text/javascript"></script>
	<link href="rs/css/index.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<div id="container">
	<ul id="nav">
		<li><a href="#">首页</a></li>
		<li><a href="#">私人网格</a></li>
		<li><a href="#">公共网格</a></li>
		<li><a href="#">帮助</a></li>
	</ul>
	<div id="left-side">
		LEFT
	</div>
	<div id="main">
		<ul class="post">
			<textarea id="input" style="width:500px;height:100px;"></textarea>
			<button>发表</button>
		</ul>
		<div class="post-stream">
			<!-- <hr /> -->
			<div id="1101" class="post-box">
				<div class="avatar-block">
					<a href="javascript:void(1);"><img class="avatar" src="rs/img/miao.jpg"></img></a>
				</div>
				<div>
					<a class="author" href="javascript:void(1);">很厉害的傻瓜</a>
					<p class="content">
						Flipboard真是一个奇迹。如果你有iPad或是对平板电脑感兴趣的话，那你很可能听说过Flipboard。Flipboard正如它自己所标榜的那样，是“社会化杂志”。它是一款免费的应用程序，让人们能够在一个比杂志更像杂志的界面上，浏览多个SNS服务和RSS订阅。在众多iPad程序中，Flipboard取得了很多第一。
					</p>
					<p class="post-comments" style="display:none;">
						<span id="5150">Comment</span>
					</p>
				</div>
			</div>

			<!-- <hr /> -->
			<div id="1102" class="post-box">
				<div class="avatar-block">
					<a href="javascript:void(1);"><img class="avatar" src="rs/img/w-sys.jpg"></img></a>
				</div>
				<div>
					<a class="author" href="javascript:void(1);">WJP</a>
					<p class="content">
						
						This is a new age! See <a href="http://springsource.org/">New Age</a>. Spring MVC is a great web! See <a href="http://springsource.org/">Great Web</a>. The first maintenance release in the Spring 3.2.x line is now available via Maven Central, the SpringSource repository, or for direct download from our community download page. 
					</p>
					<p class="post-comments" style="display:none;">
						<span id="5150">Comment</span>
					</p>
				</div>
			</div>

			<!-- <hr /> -->
		</div>
	</div>
	<div id="right-side">
		RIGHT
	</div>
	<footer id="footer">
		<p>Copyright 2013 by ...</p>
		<p>版权所有</p>
	</footer>
</div>
<script type="text/javascript" src="rs/js/dojo.js"></script>
<script type="text/javascript" src="rs/js/ajax.js"></script>
</body>
</html>