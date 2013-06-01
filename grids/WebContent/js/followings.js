'use strict';

$(document).ready(function(){
	$(".following-json").each(function(){
		var uc = $.parseJSON($(this).text());
		createUserCard(uc)
			.css('margin', '10px')
			.css('float', 'left')
			.appendTo($('#list'));
	});
});