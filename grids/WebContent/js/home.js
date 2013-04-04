'use strict';

function renderIstream(stream) {
	var streamNode = dojo.query('.stream')[0];	
	
	var rowUnder = function(parent) {
		return dojo.create('div', {className: 'row'}, parent);
	};
	dojo.forEach(stream.items, function(item){
		var itemNode = dojo.create('div', {className: 'item row'}, streamNode);
		
		var avatarNode = dojo.create('div', {className: 'avartar span1'}, itemNode);
		dojo.create('img', {src: '/grids/rs/img/wang.jpg'},
				authorLink(item.authorId, avatarNode));
		
		var tweetNode = dojo.create('div', {className: 'tweet span7'}, itemNode);
		var row1 = rowUnder(tweetNode);
		dojo.create('b', {innerHTML: item.authorName, className: 'author'},
				authorLink(item.authorId, row1));
		dojo.create('p', {innerHTML: item.content}, row1);
		var row2 = rowUnder(tweetNode);
		dojo.create('a', {innerHTML: new Date(item.date).toLocaleString()}, row2);
		dojo.create('a', {innerHTML: showTags(item.tags)}, row2);		
	});
}
function authorLink(authorId, parent) {
	var node = dojo.create('a', {uid: authorId, href: '#' + authorId}, parent);
	dojo.connect(node, 'mouseover', getUserCard);
	dojo.connect(node, 'mouseout', destroyUserCard);
	return node;
};

function renderUserCard(target, card) {
	var locator = dojo.position(target, true);
	var pLeft = locator.x;
	var pTop = locator.y + locator.h;
	var popup = dojo.create('div', {
			className: 'user-card',
			style: {
				position: 'absolute', width: '300px',
				background: 'pink', borderRadius: '3px'
			}
		},
		dojo.query('body')[0]);
	dojo.style(popup, {left: pLeft+'px', top: pTop+'px'});
	dojo.create('p', {innerHTML: card.name}, popup);
	dojo.create('p', {innerHTML: card.intro}, popup);
	dojo.create('p', {innerHTML:
		'followings: '+card.followingCount+' followers: '+card.followerCount}, popup);
}

function showTags(tags) {
	var str = '';
	dojo.forEach(tags, function(tag){
		str += tag.name;
		str += ' ';
	});
	return str;
}

function getIstream() {
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
}

function getUserCard(event) {
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
			window.alert("usercard Oops! " + resp);
		}
	});
}

function destroyUserCard(event) {
	dojo.query('.user-card').forEach(function(each){
		dojo.destroy(each);
	});
}

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