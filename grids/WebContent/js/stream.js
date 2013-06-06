'use strict';

function getIstream() {
	$.get('/grids/read/istream', {})
		.done(function(resp){
			if (resp == null) alert('stream is null');
			else createIstream(resp);
		})
		.fail(function(resp){
			window.alert('istream Oops! ' + resp);
		});
}

function createIstream(stream) {
	var streamNode = $('.stream');
	$.each(stream.items, function(idx, item){
		if (item.type == 'TweetCard') {
			createTweetCard(item).appendTo(streamNode);
		}
		else if (item.type == 'CombineGroup') {
			createCombineGroup(item).appendTo(streamNode);
		}
	});
	$('.avatar, .author-name')
		.mouseenter(launchUcOpener)
		.mouseleave(launchUcCloser);
}

function createTweetCard(card) {
	var $tc = $('.proto > .tweet').clone();
	
	var authorLinkAttrs = {uid: card.authorId, href: '#user/'+card.authorId};
	$tc.find('.avatar').attr(authorLinkAttrs)
		.find('img').attr('src', card.avatar ? card.avatar : '/grids/rs/img/panda.jpg');	
	$tc.find('.author-name').attr(authorLinkAttrs).text(card.authorName);
	$tc.find('.content').html(card.content);
	if (card.origin)
		$tc.find('.origin').replaceWith(createOriginCard(card.origin));
	else
		$tc.find('.origin').remove();
	
	$tc.find('.time').text(showTime(card.time)).attr('href', '#tw/'+card.id);
	var $tags = $tc.find('.tags');
	var tags = card.origin ? card.origin.tags : card.tags;
	if (tags && tags.length > 0) {
		$tags.html('');
		$.each(card.tags, function(idx, tag){
			createTagLabel(tag).appendTo($tags);
		});
	}
	else {
		$tags.remove();
	}
	
	if (card.forwardCount > 0) {
		$tc.find('.forward .count').text('('+card.forwardCount+')');
	}
	if (card.commentCount > 0) {
		$tc.find('.comment .count').text('('+card.commentCount+')');
	}
	$tc.find('.forward').click(function(){});
	$tc.find('.comment').click(function(){
		event.preventDefault();
		var clKey = 'comment-list';
		var $cl = $(this).data(clKey);
		console.log($cl);
		if ($cl) {
			$cl.remove();
			$(this).removeData(clKey);
		}
		else {
			$cl = createCommentList(card.id).appendTo($tc);
			$(this).data(clKey, $cl);
		}
	});
	return $tc;
}

function createOriginCard(origin) {
	var $oc = createTweetCard(origin).removeClass('item').addClass('origin');
	$oc.find('.avatar').remove();
	$oc.find('.tags').remove();
	return $oc;
}

function createCombineGroup(group) {
	var $cg = $('.proto > .combine').clone();
	$.each(group.forwards, function(idx, forward){
		createTweetCard(forward).removeClass('item').addClass('forward').appendTo($cg);
	});
	createOriginCard(group.origin).addClass('offset1').appendTo($cg);
	return $cg;
}

function createBlogData(data) {
	var $bd = $('.proto > .blog').clone();

	var authorLinkAttrs = {uid: data.authorId, href: '#user/'+data.authorId};
	$bd.find('.avatar').attr(authorLinkAttrs)
		.find('img').attr('src', data.author.avatar ? data.author.avatar : '/grids/rs/img/panda.jpg');	
	$bd.find('.author-name').attr(authorLinkAttrs).text(data.author.name);
	$bd.find('.title').text(data.title);
	$bd.find('.content').html(data.content);
	$bd.find('.time').text(showTime(data.time)).attr('href', '#bd/'+data.id);

	var $tags = $bd.find('.tags');
	var tags = data.tags;
	if (tags && tags.length > 0) {
		$tags.html('');
		$.each(data.tags, function(idx, tag){
			createTagLabel(tag).appendTo($tags);
		});
	}
	else {
		$tags.remove();
	}

	return $bd;
}

function addForwardAction($forward) {
	$forward.click(function(){

	});
}

function createCommentList(tweetId) {
	var $cl = $('<div>');
	var $loading = $('<div>').text('评论加载中').appendTo($cl);

	var $list = $('<ul>').appendTo($cl);
	$.get('/grids/read/'+tweetId+'/comments')
	.done(function(resp){
		$.each(function(idx, item){
			var $li = $('<li>').appendTo($list);
			$('<a>').append($('<img>').attr('src', '')).appendTo($li);
			$('<a>').text(item.authorName).appendTo($li);
			$('<p>').text(item.content).appendTo($li);
			$('<a>').text(showTime(item.time));
		});
		$('<div>').text('评论').replaceAll($loading);
	})
	.fail(function(){
		$('<div>').text('评论加载失败').replaceAll($loading)
	});

	return $cl;
}

function showTime(time) {
	return new Date(time).toLocaleString();
}