"use strict";

function appendIstream(stream) {
	var section = dojo.query('.r-side')[0];
	section.innerHTML = wrapInTag(stream, 'p');
}

function displayUserCard(card) {
	var section = dojo.query('.l-side')[0];
	section.innerHTML = wrapInTag(card.name, 'p') + wrapInTag(card.intro, 'p');
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
			else appendIstream(resp);
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