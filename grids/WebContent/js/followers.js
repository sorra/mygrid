'use strict';

$(document).ready(function(){
	$(".follower-json").each(function(){
		var uc = $.parseJSON($(this).text());
		createUserCard(uc)
			.css('margin', '10px')
			.css('float', 'left')
			.appendTo($('#list'));
	});
});