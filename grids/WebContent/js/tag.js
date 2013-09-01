function getTagChain(id, $parent) {
	$.get('/grids/tag/card/'+id, {})
	.done(function(resp){
		createTagChain(resp).appendTo($parent);
	})
	.fail(function(resp){
		console.log(resp);
	});
}

function createTagChain(tagCard) {
	$tch = $('.proto > .tag-chain').clone().css({position: 'relative'});
	for (var i = tagCard.chainUp.length-1, inc = 0; i >= 0; i--, inc++) {
		var item = tagCard.chainUp[i];

		$tag = $('<a></a>').addClass('tag btn').addClass('btn-info').appendTo($tch);
		$tag.text(item.name).attr('href', '/grids/public/'+item.id);
		$tag.css({display:	'block',
				  width:	'58px',
				  height:	'23px',
				  padding:	'0',
				  margin:	'0'});
		var pleft = inc*(60+50);
		$tag.css({position:	'absolute',
				  left: pleft+'px',
				  top: '0px'});
		if (i == 0) {
			$tag.removeClass('btn-info').addClass('btn-success');
		}
		
		if (i > 0) {
			$tag.click('gotoTag('+item.id+');');
			$line = $('<div></div>').addClass('line').appendTo($tch);
			$line.css({width:	'50px',
					   height:	'5px',
					   background:	'#CCCCCC'});
			var pleft = inc*(60+50) + 60;
			$line.css({position: 'absolute',
					   left: pleft+'px', 
					   top: '10px'});
		}
	};
	return $tch;
}

function createTagLabel(tagLabel) {
	$tl = $('.proto > .tag-label').clone();
	$tl.text(tagLabel.name);
	$tl.attr('tag-id', tagLabel.id);
	$tl.attr('href', "/grids/public/"+tagLabel.id);
	$tl.click('gotoTag('+tagLabel.id+');');
	if (tagLabel.chainStr) {
		$tl.attr('title', tagLabel.chainStr);
	}
	return $tl;
}

function buildTagTree(funcCreatTag, $tagTree, tag, depth, isLastOne) {
	if (depth == undefined) {
		depth = -1;
	}
	var indentValue = 20 * depth;
	var hasChildren = tag.children && tag.children.length > 0;

	if (depth >= 0) {
		var $tag = funcCreatTag(tag);
		$tag.css('margin-left', indentValue+'px')
			.appendTo($tagTree).after($('<br/>'));
		if ((depth==0 || isLastOne) && !hasChildren) {
			$tag.css('margin-bottom', '10px');
		}
	}
	if (hasChildren) {
		for (var i = 0; i < tag.children.length; i++) {
			var cur = tag.children[i];
			buildTagTree(cur, depth+1, i==tag.children.length-1);
		}
	}
}

function gotoTag (id) {
	window.open('#tag/'+id);
}