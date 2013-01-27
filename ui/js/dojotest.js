"use strict";

var hey = dojo.query('#hey')[0];
hey.innerHTML = "Loading";
dojo.xhrGet({
	url: 'http://book.360buy.com/10057809.html',
	timeout: 5000,
	load: function(resp, io_args) {
		hey.innerHTML = '<pre>' + resp + '</pre>';
	},
	error: function(error, io_args) {
		hey.innerHTML = '<p class="error">Error</p>';
	}
});