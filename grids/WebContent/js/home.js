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

	buildTagSels();
	$('.tag-plus').click(function(event){
		event.preventDefault();
		$(this).popover('toggle');
	});
	$('form.top-box .btn[type="submit"]').click(function(event){
		event.preventDefault();
		var $submit = $(this);
		$submit.prop('disabled', true);

		var selectedTagIds = [];
		$('.tag-sel.btn-success .tag-label').each(function(idx){
			var tagId = parseInt($(this).attr('tag-id'));
			selectedTagIds.push(tagId);
			$(this).removeClass('.btn-success');
		});

		$.post('/grids/post/tweet', {
			content: $('form.top-box .input').val(),
			tagIds: selectedTagIds
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
});

function buildTagSels() {
	var userSelf = $.parseJSON($('#self-json').text());
	var $protoTag = $('.tag-sel');
	$.each(userSelf.topTags, function(idx, item){
		var $tag = $protoTag.clone().html('').click(function(event){
			event.preventDefault();
			$(this).toggleClass('btn-success');
		});
		createTagLabel(item).appendTo($tag);
		$tag.insertBefore($protoTag);
	});
	$protoTag.remove();
}
