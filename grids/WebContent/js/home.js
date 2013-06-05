'use strict';

$(document).ready(function() {
	$.post('/grids/auth/login', {
		email : 'admin@',
		password : '123'})
	.done(function(resp) {
		window.userSelf = $.parseJSON($('#user-self-json').text());
		var $selfCard = createUserCard(userSelf).css('border', '').css('border-radius', '');
		$selfCard.find('.follow').remove();
		$selfCard.appendTo($('.side'));
	})
	.fail(function(resp) {
		window.alert("login failed! " + resp);
	});
	buildTagSels();
	buildTagPlus();

	$('form.top-box .btn[type="submit"]')
	.tooltip({
		placement: 'top',
		trigger: 'manual'
	})
	.click(function(event){
		event.preventDefault();
		var $submit = $(this);
		$submit.prop('disabled', true);

		var selectedTagIds = [];
		$('.tag-sel.btn-success').each(function(idx){
			var tagId = parseInt($(this).attr('tag-id'));
			selectedTagIds.push(tagId);
			$(this).removeClass('.btn-success');
		});

		var input = $('form.top-box .input').val();
		if (input.trim().length == 0) {
			postTweetFail();
			$submit.prop('disabled', false);
			return;
		} 

		$.post('/grids/post/tweet', {
			content: input,
			tagIds: selectedTagIds
		})
		.always(function(resp){
			$submit.prop('disabled', false);
		})
		.done(function(resp){
			console.log(resp);
			if (resp == true) postTweetDone();
			else postTweetFail();
		})
		.fail(function(resp){
			postTweetFail();
		});
	});

	getIstream();
});

function postTweetDone() {
	var $submit = $('form.top-box .btn[type="submit"]');
	$('form.top-box .input').val('');
	$submit.data('tooltip').options.title = '发表成功';
	$submit.tooltip('show');
	window.setTimeout(function(){$submit.tooltip('hide');}, 1000);
}

function postTweetFail() {
	var $submit = $('form.top-box .btn[type="submit"]');
	$submit.data('tooltip').options.title = '发表失败';
	$submit.tooltip('show');
	window.setTimeout(function(){$submit.tooltip('hide');}, 1000);
}