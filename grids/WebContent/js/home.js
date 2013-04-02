"use strict";

function renderIstream(stream) {
	var streamDiv = dojo.query('.stream')[0];
	var streamHtml = '';
	dojo.forEach(stream.items, function(item){
		var itemHtml = '<div class="item row">';
		
		itemHtml += '<div class="avatar span1">';
		itemHtml += ('<a href="#' + item.authorId + '">');
		itemHtml += ('<img src="' + '/grids/rs/img/wang.jpg'/*item.avatar*/ + '"/></a></div>');
		
		itemHtml += '<div class="tweet span7">';
		itemHtml += ('<div class="row"><a class="author" href="#' + item.authorId +
				'"><b>' + item.authorName + '</b></a>');
		itemHtml += (wrapInTag(item.content, 'p') + '</div>');
		itemHtml += ('<div class="row"><a>' + new Date(item.date).toLocaleString()
				+ ' </a><a>' + showTags(item.tags) + '</a></div>');
		itemHtml += '</div>';
		
		itemHtml += '</div>';
		streamHtml += itemHtml;
	});
	streamDiv.innerHTML = streamHtml;
}

function displayUserCard(card) {
	var section = dojo.query('.l-side')[0];
	section.innerHTML = wrapInTag(card.name, 'p') + wrapInTag(card.intro, 'p');
}

function showTags(tags) {
	var str = '';
	dojo.forEach(tags, function(tag){
		str += tag.name;
		str += ' ';
	});
	return str;
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