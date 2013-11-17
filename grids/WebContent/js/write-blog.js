'use strict';

$(document).ready(function(){
	buildTagSels();
	buildTagPlus();

	$('form.blog .btn[type=submit]')
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
			console.log('fff');
		});

		$.post(webroot+'/post/blog', {
			title: $('.blog .title').val(),
			content: $('.blog .content').val(),
			tagIds: selectedTagIds
		})
		.always(function(resp){
			$submit.prop('disabled', false);
		})
		.done(function(resp){
			if (resp == true) postBlogDone();
			else postBlogFail();
		})
		.fail(function(resp){
			postBlogFail();
		});
	});
});

function postBlogDone() {
	var $submit = $('form.blog .btn[type=submit]');
    tipover($submit, '发表成功', 1000);
}

function postBlogFail() {
	var $submit = $('form.blog .btn[type=submit]');
    tipover($submit, '发表失败', 1000);
}