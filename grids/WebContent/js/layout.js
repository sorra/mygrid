function buildNavTagTree($lnk, tagTree) {
    var $navTagTree = $('<div>');
    buildTagTree(function(tag){
        return createTagLabel(tag);
    }, $navTagTree, tagTree);
    $lnk.popover({
            html: true,
            placement: 'bottom',
            trigger: 'manual',
            selector: '#tag-tree-popover',
            content: $navTagTree
    }).popover('show');
}

$(document).ready(function(){
    $.get('/grids/tag/tree', {})
    .done(function(resp){
        $('#nav-tags').click(function(event){
            event.preventDefault();
            buildNavTagTree($(this), resp);
          });
    })
    .fail(function(resp){
        console.log(resp);
    });
});