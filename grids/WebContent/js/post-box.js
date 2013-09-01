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
	var $tagTree = $('<div></div>');
	
	function buildTagTree(node, depth, isLastOne) {
		var indentValue = 20 * depth;
		var hasChildren = node.children && node.children.length > 0;
		if (depth >= 0) {
			var $tagSel = createTagSel(node);
			$tagSel.css('margin-left', indentValue+'px')
				.appendTo($tagTree).after($('<br/>'));
			if ((depth==0 || isLastOne) && !hasChildren) {
				$tagSel.css('margin-bottom', '10px');
			}
		}
		if (hasChildren) {
			for (var i = 0; i < node.children.length; i++) {
				var cur = node.children[i];
				buildTagTree(cur, depth+1, i==node.children.length-1);
			}
		}
	}
	buildTagTree(tagTree, -1);

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