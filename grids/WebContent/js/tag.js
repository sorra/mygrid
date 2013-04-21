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
		$tag.text(item.name).attr('href', '#tag/'+item.id);
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
					   background:	'#DDDDDD'});
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
	$tl.attr('href', tagLabel.id);
	$tl.attr('title', tagLabel.chainStr);
	return $tl;
}

function gotoTag (id) {
	window.open('#tag/'+id);
}