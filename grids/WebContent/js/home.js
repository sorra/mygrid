"use strict";

function appendIstream(stream) {
	var section = dojo.query('.r-side')[0];
	section.innerHTML = wrapInHtml(stream, 'p');
}

function displayUserCard(card) {
	var section = dojo.query('.l-side')[0];
	section.innerHTML = wrapInHtml(card.name, 'p') + wrapInHtml(card.intro, 'p');
}

function wrapInHtml(content, tagName) {
	return '<'+tagName+'>'+content+'</'+tagName+'>';
}

dojo.xhrGet({
	url: "/grids/read/istream",
	timeout: 1000,
	handleAs: 'json',
	load: function(resp, io_args){
		appendIstream(resp);
	},
	error: function(resp, io_args) {
		window.alert("istream Oops! " + resp);
	}
});
dojo.xhrGet({
	url: "/grids/user/card/0",
	timeout: 1000,
	handleAs: 'json' ,
	load: function(resp, io_args){
		displayUserCard(resp);
	},
	error: function(resp, io_args) {
		window.alert("card Oops! " + resp);
	}
});