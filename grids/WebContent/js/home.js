'use strict';

$(document).ready(function() {
	$.post('/grids/auth/login', {
		email : 'admin@',
		password : '123'})
	.done(function(resp) {
		getIstream();
	})
	.fail(function(resp) {
		window.alert("login failed! " + resp);
	});

	$('form.top-box .btn[type="submit"]').click(function(event){
		event.preventDefault();
		var $submit = $(this);
		$submit.prop('disabled', true);
		$.post('/grids/post/tweet', {
			content: $('form.top-box .input').val(),
			tagIds: []
		})
		.always(function(resp){
			$submit.prop('disabled', false);
		})
		.done(function(resp){
			$('form.top-box .input').val('发表成功！');
		})
		.fail(function(resp){
			window.alert("post failed! " + resp);
		});
	});

	$.get('/grids/user/self')
	.done(function(resp) {
		window.userSelf = resp;
	})
	.fail(function(resp) {
		window.alert("getting self info failed! " + resp);
	});
});

