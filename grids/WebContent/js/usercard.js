'use strict';
window.ucOpener;
window.ucCloser;

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
			else enhancedUserCard(target, resp).hide()
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

function enhancedUserCard(target, card) {
	console.log("create");	
	$uc = createUserCard(card);
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
	$uc.find('.avatar > a').find('img').attr('src', '/grids/rs/img/panda.jpg');
	$uc.find('.name').text(card.name);
	$uc.find('.intro').text(card.intro);
	$uc.find('.following-count').text(card.followingCount);
	$uc.find('.follower-count').text(card.followerCount);
	$uc.css({width: '300px', background: '#f0f0f0', borderRadius: '3px'});
	
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