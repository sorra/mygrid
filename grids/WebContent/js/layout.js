'use strict';

function buildNavTagTree($lnk, tagTree) {
    var $navTagTree = $('<div>');
    buildTagTree(function(tag){
        return createTagLabel(tag).addClass('btn');
    }, $navTagTree, tagTree);
    $lnk.popover({
            html: true,
            placement: 'bottom',
            trigger: 'manual',
            selector: '#tag-tree-popover',
            content: $navTagTree
    });
}

$(document).ready(function(){
    $.get('/grids/tag/tree', {})
    .done(function(resp){
        var $navTags = $('#nav-tags')
          .click(function(event){
            event.preventDefault();
            $(this).popover('toggle');
          });
        buildNavTagTree($navTags, resp);
    })
    .fail(function(resp){
        console.log(resp);
    });
});