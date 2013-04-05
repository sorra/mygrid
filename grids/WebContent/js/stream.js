'use strict';

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
			window.alert('istream Oops! ' + resp);
		}
	});
}

function renderIstream(stream) {
	var streamNode = dojo.query('.stream')[0];	
	
	dojo.forEach(stream.items, function(item){
		if (item.type == 'TweetCard') {
			dojo.place(renderTweetCard(item), streamNode);
		}
		else if (item.type == 'CombineGroup') {
			dojo.place(renderCombineGroup(item), streamNode);
		}
	});
}

function renderTweetCard(card) {
	var cardNode = dojo.create('div', {className: 'item row'});
	renderTweetDetail(card, cardNode);
	return cardNode;
}

function renderCombineGroup(group) {
	var groupNode = dojo.create('div', {className: 'item row'});	
	dojo.forEach(group.forwards, function(forward){
		var row = bsRow(groupNode);
		dojo.addClass(row, 'span8');
		renderTweetDetail(forward, row);
	});	
	var rowOrigin = bsRow(groupNode);
	dojo.addClass(rowOrigin, 'origin');
	dojo.addClass(rowOrigin, 'span8');
	renderTweetDetail(group.origin, rowOrigin);
	
	return groupNode;
}

function renderTweetDetail(tweet, parent) {
	var avatarNode = dojo.create('div', {className: 'avartar span1'}, parent);
	dojo.create('img', {src: '/grids/rs/img/wang.jpg'},
			authorLink(tweet.authorId, avatarNode));
	
	var tweetNode = dojo.create('div', {className: 'tweet span7'}, parent);
	var row1 = bsRow(tweetNode);
	dojo.create('b', {innerHTML: tweet.authorName, className: 'author'},
			authorLink(tweet.authorId, row1));
	dojo.create('p', {innerHTML: tweet.content}, row1);
	//TODO render origin
	var row2 = bsRow(tweetNode);
	dojo.create('a', {innerHTML: new Date(tweet.date).toLocaleString()}, row2);
	if (tweet.tags.length > 0) dojo.create('a', {innerHTML: showTags(tweet.tags)}, row2);
}

function bsRow(parent) {
	return dojo.create('div', {className: 'row'}, parent);
};

function authorLink(authorId, parent) {
	var node = dojo.create('a', {uid: authorId, href: '#' + authorId}, parent);
	dojo.connect(node, 'mouseover', function(event){
		if (window.userCardRemover && window.userCardRemover.locator == event.currentTarget) {
			clearUserCardRemover();
		}
		else getUserCard(event);
	});
	dojo.connect(node, 'mouseout', removeUserCard);
	return node;
};

function showTags(tags) {
	var str = '';
	dojo.forEach(tags, function(tag){
		str += tag.name;
		str += ' ';
	});
	return str;
}