$(document).ready(function(){
	$.get('/grids/tag/card/8', {})
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

	var chainTitle ='';
	for (var i = tagCard.chainUp.length - 1; i > 0; i--) {
		chainTitle += tagCard.chainUp[i].name;
		chainTitle += '->';
	}
	chainTitle += tagCard.chainUp[0].name;

	$tagc.attr('title', chainTitle);
	return $tagc;
}