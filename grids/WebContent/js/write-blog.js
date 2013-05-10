'use strict';

$(document).ready(function(){
	buildTagSels();
	$('.blog button[type=submit]').click(function(event){
		event.preventDefault();
		var $submit = $(this);
		$submit.prop('disabled', true);

		var selectedTagIds = [];
		$('.tag-sel.btn-success .tag-label').each(function(idx){
			var tagId = parseInt($(this).attr('tag-id'));
			selectedTagIds.push(tagId);
			$(this).removeClass('.btn-success');
		});

		$.post('/grids/post/blog', {
			title: $('.blog .title').val(),
			content: $('.blog .content').val(),
			tagIds: selectedTagIds
		})
		.always(function(resp){
			$submit.prop('disabled', false);
		})
		.done(function(resp){
			$('.blog .content').val('发表成功！');
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