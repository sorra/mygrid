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
	buildTagPlus();
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

function createTagSel(tagLabel) {
	return createTagLabel(tagLabel).addClass('tag-sel').addClass('btn btn-small')
			.click(function(){$(this).toggleClass('btn-success');});
}

function buildTagSels() {
	var userSelf = $.parseJSON($('#user-self-json').text());
	$.each(userSelf.topTags, function(idx, item){
		createTagSel(item).insertBefore($('.tag-plus'));
	});
}

function buildTagPlus() {
	var tagTree = $.parseJSON($('#tag-tree-json').text());
	var $tagTree = $('<div></div>');
	
	function buildTagTree(node, depth) {
		var indentValue = 20 * depth;
		if (depth >= 0) {
			createTagSel(node).css('display', 'block')
				.css('margin-left', indentValue+'px').appendTo($tagTree);
		}
		if (node.children && node.children.length > 0) {
			for (var i = 0; i < node.children.length; i++) {
				buildTagTree(node.children[i], depth+1);
			};
		}
	}
	buildTagTree(tagTree, -1);

	$('.tag-plus').popover({
			html: true,
			placement: 'bottom',
			trigger: 'manual',
			content: $tagTree
	}).click(function(){
		if ($(this).data('show-popover')) {
			$(this).data('show-popover', false)
				   .popover('hide');
		}
		else {
			$(this).data('show-popover', true)
				   .popover('show');
		}
	});
}