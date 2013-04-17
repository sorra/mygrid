$(document).ready(function(){
	$.get('/grids/tag/card/5', {})
	.done(function(resp){
		createTagCard(resp).appendTo($('body > .container'));
	})
	.fail(function(resp){
		alert(resp);
	});
});

function createTagCard(tagCard) {
	$tagc = $('.proto > .tag-card').clone();
	$tagc.text(tagCard.name);
	$tagc.attr('href', tagCard.id);
	$tagc.attr('title', tagCard.chainUp);
	return $tagc;
}