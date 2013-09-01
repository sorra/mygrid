'use strict';

function createTagSel(tagLabel) {
	return createTagLabel(tagLabel).addClass('tag-sel').addClass('btn btn-small')
			.click(function(event){
			    event.preventDefault();
			    $(this).toggleClass('btn-success');
			  });
}

function buildTagSels() {
	var userSelf = $.parseJSON($('#user-self-json').text());
	$.each(userSelf.topTags, function(idx, item){
		createTagSel(item).insertBefore($('.tag-plus'));
	});
}

function buildTagPlus() {
	var tagTree = $.parseJSON($('#tag-tree-json').text());
	var $tagTree = $('<div>');
	
	buildTagTree(createTagSel, $tagTree, tagTree);

	$('.tag-plus').popover({
			html: true,
			placement: 'bottom',
			trigger: 'manual',
			selector: '#tag-tree-popover',
			content: $tagTree
	}).popover('show');
	$('.tag-plus').data('popover').tip().hide();

	$('.tag-plus').click(function(){
		if ($(this).data('show-popover')) {
			$(this).data('show-popover', false)
				   .data('popover').tip().hide();
		}
		else {
			$(this).data('show-popover', true)
				   .data('popover').tip().show();
		}
	});
}