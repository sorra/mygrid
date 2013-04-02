"use strict";

function renderIstream(stream) {
	var streamDiv = dojo.query('.stream')[0];
	var streamHtml = '';
	dojo.forEach(stream.items, function(item){
		var itemHtml = '<div class="item row">';
		itemHtml += '<div class="avatar span1">';
		itemHtml += ('<a href="#' + item.authorId + '">');
		itemHtml += ('<img src="' + item.avatar + '"/></a>');
		itemHtml += '</div>';
		itemHtml += '<div class="tweet span7">';
		itemHtml += ('<a class="author" href="#' + item.authorId +
				'"><b>' + item.authorName + '</b></a>');
		itemHtml += wrapInTag(item.content, 'p');
		itemHtml += '</div></div>';
		streamHtml += itemHtml;
	});
	streamDiv.innerHTML = streamHtml;
//<div class="item row">
//	<div class="avatar span1"><a href="#"><img src="rs/img/dingcl.jpg"/></a></div>
//	<div class="tweet span7">
//		<a class="author" href="#"><b>丁辰灵</b></a>
//		<p>【一张决策图来重新思考你的网站】你至少要考虑四个方面：目标&客户；内容&结构；设计；技术。为什么要建立自己的站点？内容和结构如何安排？网站设计能否脱颖而出？你的技术支持是否足够聪明和稳定？是时候重新思考你的网站！@图说PicSays “3W互联网深度精选”（微信studywww），最有价值深度干货。</p>
//	</div>
//</div>
}

function displayUserCard(card) {
	var section = dojo.query('.l-side')[0];
	section.innerHTML = wrapInTag(card.name, 'p') + wrapInTag(card.intro, 'p');
}

function wrapInTag(content, tagName) {
	return '<'+tagName+'>'+content+'</'+tagName+'>';
}

function afterLogin() {
	dojo.xhrGet({
		url : '/grids/read/istream',
		timeout : 1000,
		handleAs :'json',
		load : function(resp, io_args) {
			if (resp == null)
				alert('stream null');
			else renderIstream(resp);
		},
		error : function(resp, io_args) {
			window.alert("istream Oops! " + resp);
		}
	});
	dojo.xhrGet({
		url : '/grids/user/card/1',
		timeout : 1000,
		handleAs : 'json',
		load : function(resp, io_args) {
			if (resp == null)
				alert('usercard null');
			else displayUserCard(resp);
		},
		error : function(resp, io_args) {
			window.alert("usercard Oops! " + resp);
		}
	});
}

dojo.addOnLoad(function() {
	dojo.xhrPost({
		url : '/grids/auth/login',
		timeout : 1000,
		content : {email: 'admin@', password: '123'},
		load : function(resp, io_args) {
			afterLogin();
		},
		error : function(resp, io_args) {
			window.alert("login failed! " + resp);
		}
	});
});