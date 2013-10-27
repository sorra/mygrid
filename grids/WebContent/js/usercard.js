'use strict';
window.ucOpener;
window.ucCloser;

function launchUcOpener() {
	if (this == undefined) {
		console.log("launchUcOpener's this is undefined");
	}
	if (window.ucCloser && window.ucCloser.locator == this) {
		cancelUcCloser();
//		return;
	}

	cancelUcOpener();
	window.ucOpener = {
		timer : window.setTimeout($.proxy(openUserCard, this), 200),
		locator: this,
		uid: $(this).attr('uid')
	};
}

function launchUcCloser() {
	if (this == undefined) {
		console.log("launchUcCloser's this is undefined");
	}
	cancelUcOpener();

	cancelUcCloser();
	window.ucCloser = {
		timer: window.setTimeout(closeUserCard, 200),
		locator: this,
		uid: $(this).attr('uid')
	};
}

function openUserCard() {
	var target = this;
	cancelUcOpener();
	closeUserCard();
	console.log("open");
	$.get('/sage/user/card/' + $(target).attr('uid'), {})
		.done(function(resp) {
			if (resp == null)
				console.log('usercard null');
			else createPopupUserCard(target, resp).hide()
					.appendTo($('body')).fadeIn();
		})
		.fail(function(resp) {
			console.log('usercard Oops! ' + resp);
		});
}

function closeUserCard() {
	cancelUcCloser();
	console.log("close");
	var $uc = $('.user-card.popup:not(.proto *)');
	$uc.fadeOut('', function(){$uc.remove();});
}

function createPopupUserCard(target, card) {
	console.log("create");	
	var $uc = createUserCard(card).addClass('popup');
	if (window.userSelf && window.userSelf.id == card.id) {
		$('<button>').addClass('btn span').text('自己')
			.replaceAll($uc.find('.follow'));
	}
	var locatorPos = $(target).offset();
	var pleft = locatorPos.left;
	var ptop = locatorPos.top + $(target).height();
	$uc.css({
		position: 'absolute',
		left: pleft+'px', top: ptop+'px'
		});	
	$uc.mouseenter(cancelUcCloser).mouseleave($.proxy(launchUcCloser, target));

	return $uc;
}

function createUserCard(card) {
	var $uc = $('.proto > .user-card').clone();
	$uc.find('.avatar > a').attr(userLinkAttrs(card.id)).find('img').attr('src', card.avatar);
	$uc.find('.name').text(card.name);
	$uc.find('.intro').text(card.intro);
	$uc.find('.followings').attr('href', '/sage/followings/'+card.id);
	$uc.find('.followers').attr('href', '/sage/followers/'+card.id);
	$uc.find('.following-count').text(card.followingCount);
	$uc.find('.follower-count').text(card.followerCount);
	$uc.css({
		minWidth: '200px', background: '#f0f0f0', padding: '2px',
		border: 'orange solid 2px', borderRadius: '8px'
	});

	var $follow = $uc.find('.follow');
	if (card.isFollowing) {
		setAsFollowed($follow, card);
	}
	else {
		setAsNotFollowed($follow, card);
	}
	return $uc;
}

function cancelUcOpener() {
	if (window.ucOpener) {
		window.clearTimeout(window.ucOpener.timer);
		window.ucOpener = null;
	}
}

function cancelUcCloser() {
	if (window.ucCloser) {
		window.clearTimeout(window.ucCloser.timer);
		window.ucCloser = null;
	}
}

function setAsFollowed($follow, uc) {
	$follow.text('已关注').addClass('btn btn-success span pull-right').click(function(){
		var $dialog = createTagDialog(uc);
		var $body = $dialog.find('.modal-body');

		$('<button>').text('编辑关注').addClass('btn btn-primary')
		.appendTo($dialog.find('.modal-footer'))
		.click(function(){
			var selectedTagIds = [];
			$dialog.find('.modal-body')
			.find('.btn-success').each(function(){
				var tagId = parseInt($(this).attr('tag-id'));
				selectedTagIds.push(tagId);
			});

			console.log("tagIds: " + selectedTagIds);
			$.post('/sage/editfollow/'+uc.id, {tagIds: selectedTagIds})
			.fail(function(){
				alert('操作失败');
			});
			destroyTagDialog($dialog);
		});

		$('<button>').text('取消关注').addClass('btn btn-inverse')
		.appendTo($dialog.find('.modal-footer'))
		.click(function(){
			$.post('/sage/unfollow/'+uc.id)
			.fail(function(){
				alert('操作失败');
			});
			destroyTagDialog($dialog);
		});

		$dialog.appendTo($('#container')).modal();
	});
}

function setAsNotFollowed($follow, uc) {
	$follow.text('关注').addClass('btn btn-success span pull-right').click(function(){
		// follow();
		var $dialog = createTagDialog(uc);

		$('<button>').text('关注').addClass('btn btn-primary')
		.appendTo($dialog.find('.modal-footer'))
		.click(function(){
			var selectedTagIds = [];
			$dialog.find('.modal-body')
			.find('.btn-success').each(function(){
				var tagId = parseInt($(this).attr('tag-id'));
				selectedTagIds.push(tagId);
			});

			console.log("tagIds: " + selectedTagIds);
			$.post('/sage/follow/'+uc.id, {tagIds: selectedTagIds})
			.fail(function(){
				alert('操作失败');
			});
			destroyTagDialog($dialog);
		});

		$dialog.appendTo($('#container')).modal();
	});
}

function createTagDialog(uc) {
	var $dialog = $('<div>').addClass('tag-dialog modal fade')
	.css({
		width: '300px',
		minHeight: '100px',
		borderRadius: '10px'
	});
	$('<div>').addClass('modal-header').text('请选择0~n个标签').appendTo($dialog);
	var $body = $('<div>').addClass('modal-body').appendTo($dialog);
	$.each(uc.tags, function(idx, item){
		$('<button>').text(item.name).attr('tag-id', item.id).addClass('btn').appendTo($body)
		.click(function(){
			$(this).toggleClass('btn-success');
		});
	});
	$('<div>').addClass('modal-footer').appendTo($dialog);

	return $dialog;
}

function destroyTagDialog($dialog) {
	$dialog.remove();
	$('.modal-backdrop').remove();
}