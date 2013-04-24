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
		.find('img').attr('src', '/grids/rs/img/panda.jpg');	
	$tc.find('.author-name').attr(authorLinkAttrs).text(card.authorName);
	$tc.find('.content').text(card.content);
	if (card.origin)
		$tc.find('.origin').replaceWith(createOriginCard(card.origin));
	else
		$tc.find('.origin').remove();
	
	$tc.find('.time').text(showTime(card.time)).attr('href', '#tw/'+card.id);
	var $tags = $tc.find('.tags');
	var tags = card.origin ? origin.tags : card.tags;
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
		$tc.find('.forward-count').text('('+card.forwardCount+')');
	}
	if (card.commentCount > 0) {
		$tc.find('.comment-count').text('('+card.commentCount+')');
	}
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
		createTweetCard(forward).removeClass('item').appendTo($cg);
	});
	createOriginCard(group.origin).addClass('offset1').appendTo($cg);
	return $cg;
}

function showTime(time) {
	return new Date(time).toLocaleString();
}
