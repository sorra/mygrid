'use strict';
window.ucOpener;
window.ucCloser;

$(document).ready(function() {
	$.post('/grids/auth/login', {
		email : 'admin@',
		password : '123'})
		.done(function(resp) {
			getIstream();
		})
		.fail(function(resp) {
			window.alert("login failed! " + resp);
	});
	$('form.top-box .btn[type="submit"]').click(function(event){
		event.preventDefault();
		var $submit = $(this);
		$submit.prop('disabled', true);
		$.post('/grids/post/tweet', {
			content: $('form.top-box .input').val(),
			tagIds: []
		})
		.always(function(resp){
			$submit.prop('disabled', false);
		})
		.done(function(resp){
			$('form.top-box .input').val('发表成功！');
		})
		.fail(function(resp){
			window.alert("post failed! " + resp);
		});
	});
});

function launchUcOpener() {
	if (this == undefined) {
		console.log("launchUcOpener's this is undefined");
	}
	if (window.ucCloser && window.ucCloser.locator == this) {
		cancelUcCloser();
		return;
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
	$.get('/grids/user/card/' + $(target).attr('uid'), {})
		.done(function(resp) {
			if (resp == null)
				console.log('usercard null');
			else createUserCard(target, resp).hide()
					.appendTo($('body')).fadeIn();
		})
		.fail(function(resp) {
			console.log('usercard Oops! ' + resp);
		});
}

function closeUserCard() {
	cancelUcCloser();
	console.log("close");
	var $uc = $('.user-card:not(.proto *)');
	$uc.fadeOut('', function(){$uc.remove();});
}

function createUserCard(target, card) {
	console.log("create");
	var $uc = $('.proto > .user-card').clone();
	$uc.find('.avatar > a').find('img').attr('src', '/grids/rs/img/panda.jpg');
	$uc.find('.name').text(card.name);
	$uc.find('.intro').text(card.intro);
	$uc.find('.following-count').text(card.followingCount);
	$uc.find('.follower-count').text(card.followerCount);
	

	var locatorPos = $(target).offset();
	var pleft = locatorPos.left;
	var ptop = locatorPos.top + $(target).height();
	$uc.css({
		position: 'absolute', width: '300px',
		background: 'pink', borderRadius: '3px',
		left: pleft+'px', top: ptop+'px'
		});	
	$uc.mouseenter(cancelUcCloser).mouseleave($.proxy(launchUcCloser, target));

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