'use strict';
window.userCardRemover;

dojo.addOnLoad(function() {
	dojo.xhrPost({
		url : '/grids/auth/login',
		timeout : 1000,
		content : {email: 'admin@', password: '123'},
		load : function(resp, io_args) {
			getIstream();
		},
		error : function(resp, io_args) {
			window.alert("login failed! " + resp);
		}
	});
});

function getUserCard(event) {
	doRemoveUserCard();
	var target = event.currentTarget;
	dojo.xhrGet({
		url : '/grids/user/card/' + dojo.attr(target, 'uid'),
		timeout : 1000,
		handleAs : 'json',
		load : function(resp, io_args) {
			if (resp == null)
				alert('usercard null');
			else renderUserCard(target, resp);
		},
		error : function(resp, io_args) {
			window.alert('usercard Oops! ' + resp);
		}
	});
}

function renderUserCard(target, card) {
	doRemoveUserCard();
	var popup = dojo.create('div', {
			id: 'user-card',
			style: {
				position: 'absolute', width: '300px',
				background: 'pink', borderRadius: '3px'
			}
		},
		dojo.query('body')[0]);
	dojo.create('p', {innerHTML: card.name}, popup);
	dojo.create('p', {innerHTML: card.intro}, popup);
	dojo.create('p', {innerHTML:
		'followings: '+card.followingCount+' followers: '+card.followerCount}, popup);

	var locatorPos = dojo.position(target, true);
	var pLeft = locatorPos.x;
	var pTop = locatorPos.y + locatorPos.h;
	dojo.style(popup, {left: pLeft+'px', top: pTop+'px'});
	dojo.connect(popup, 'mouseover', clearUserCardRemover);
	dojo.connect(popup, 'mouseout', removeUserCard);
}

function removeUserCard(event) {
	clearUserCardRemover();
	window.userCardRemover = {
		timer: window.setTimeout(doRemoveUserCard, 200),
		locator: event.currentTarget,
		uid: dojo.attr(event.currentTarget, 'uid')
	};
}

function doRemoveUserCard() {
	clearUserCardRemover();
	dojo.destroy(dojo.byId('user-card'));
}

function clearUserCardRemover() {
	if (window.userCardRemover) {
		window.clearTimeout(window.userCardRemover.timer);
		window.userCardRemover = null;
	}
}