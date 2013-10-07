'use strict';
$.fn.outerHTML = function(s) {
    return s
        ? this.before(s).remove()
        : jQuery("<p>").append(this.eq(0).clone()).html();
};

$.fn.warnEmpty = function() {
    if (this.length == 0) {console.warn('Empty NodeList for '+this.selector+'!');}
    return this;
};

function buildNavTagTree($lnk, tagTree) {
    var $navTagTree = $('<div>');
    buildTagTree(function(tag){
        return createTagLabel(tag).addClass('btn')
            .click(function(){
                $lnk.popover('hide');
            });
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
        var $lnk = $('#nav-tags')
          .click(function(event){
            event.preventDefault();
            $(this).popover('toggle');
          });
        buildNavTagTree($lnk, resp);
    })
    .fail(function(resp){
        console.log(resp);
    });
});