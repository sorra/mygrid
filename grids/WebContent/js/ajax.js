"use strict";
dojo.xhrGet({
	url: "http://localhost:8080/grids/action/istream",
	timeout: 1000,
	load: function(resp, io_args){
		window.alert(resp);
	},
	error: function(resp, io_args) {
		window.alert("Error: " + resp);
	}
});