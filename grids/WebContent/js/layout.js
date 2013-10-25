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
(function ($) {
    $.fn.getCursorPosition = function() {
        var el = $(this).get(0);
        var pos = 0;
        if('selectionStart' in el) {
            pos = el.selectionStart;
        } else if('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    };
    $.fn.setCursorPosition = function(pos) {
        if ($(this).get(0).setSelectionRange) {
          $(this).get(0).setSelectionRange(pos, pos);
        } else if ($(this).get(0).createTextRange) {
          var range = $(this).get(0).createTextRange();
          range.collapse(true);
          range.moveEnd('character', pos);
          range.moveStart('character', pos);
          range.select();
        }
      };
})(jQuery);

function buildNavTagTree($lnk, tagTree) {
    var $navTagTree = $('<div>');
    var $createTag = $('<btn>').text('新建').addClass('btn btn-warning').css('display', 'block').appendTo($navTagTree);
    
    var $dialog = $('<div class="new-tag-dialog modal">')
        .css({
            width : '300px',
            minHeight : '100px',
            borderRadius : '10px'
        });
    $('<div class="modal-header">').text('新的标签').appendTo($dialog);
    var $body = $('<div class="modal-body">').appendTo($dialog);
    $('<input id="name">').appendTo($body);
    $('<input id="parent-id">').appendTo($body);
    var $footer = $('<div class="modal-footer">').appendTo($dialog);
    $('<button class="btn btn-primary">').text('确定').css({float: 'right'}).appendTo($footer)
        .click(function() {
            $.post('/sage/tag/new', {
                name: $dialog.find('#name').val(),
                parentId: $dialog.find('#parent-id').val()
            });
            $dialog.find('#name').val('');
            $dialog.find('#parent-id').val('');
            $dialog.modal('hide');
        });
    $createTag.click(function(){
        $dialog.modal('show');
    });
    
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
    $.get('/sage/tag/tree', {})
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