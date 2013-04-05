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