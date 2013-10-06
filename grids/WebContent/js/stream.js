'use strict';
jQuery.fn.outerHTML = function(s) {
    return s
        ? this.before(s).remove()
        : jQuery("<p>").append(this.eq(0).clone()).html();
};

function getStream(url) {
    $.get(url, {})
        .done(function(resp){
            if (resp == null) alert('stream is null');
            else createStream(resp, url);
        })
        .fail(function(resp){
            window.alert('stream Oops! ' + resp);
        });
}

function getStreamAfter(url, afterId) {
    if (afterId == undefined) throw new Error;
    $.get(url, {after: afterId})
        .done(function(resp){
            if (resp == null) alert('stream is null');
            else createStreamAfter(resp, url);
        })
        .fail(function(resp){
            window.alert('stream Oops! ' + resp);
        });
}

function getStreamBefore(url, beforeId) {
    if (beforeId == undefined) throw new Error;
    $.get(url, {before: beforeId})
        .done(function(resp){
            if (resp == null) alert('stream is null');
            else createStreamBefore(resp, url);
        })
        .fail(function(resp){
            window.alert('stream Oops! ' + resp);
        });
}

function createStream(stream, url) {
    console.log(stream.items.length);
    var $stream = $('.stream');
    var $slist = $('.slist').empty();
    if ($slist[0] == undefined) throw new Error;
    $('<a>').addClass('newfeed btn').text('看看新的').css('margin-left', '320px').prependTo($stream)
        .click(function() {
            var largest = null;
            $('.slist .tweet').each(function(){
                var id = $(this).data('id');
                if (id != undefined && id != null && (id > largest || largest == null)) {
                    largest = id;
                }
            });
            console.log("largest "+largest);
            getStreamAfter(url, largest);
        });

    $.each(stream.items, function(idx, item){
        if (item.type == 'TweetCard') {
            createTweetCard(item).appendTo($slist);
        }
        else if (item.type == 'CombineGroup') {
            createCombineGroup(item).appendTo($slist);
        }
    });

    $('<a>').addClass('morefeed btn').text('看看更早的').css('margin-left', '320px').appendTo($stream)
        .click(function() {
            var smallest = null;
            $('.slist .tweet').each(function(){
                var id = $(this).data('id');
                if (id != undefined && id != null && (id < smallest || smallest == null)) {
                    smallest = id;
                }
            });
            console.log("smallest "+smallest);
            getStreamBefore(url, smallest);
        });
}

function createStreamAfter(stream) {
    console.log(stream.items.length);
    console.log(stream);
    var $slist = $('.slist');
    $.each(stream.items, function(idx, item){
        if (item.type == 'TweetCard') {
            createTweetCard(item).prependTo($slist);
        }
        else if (item.type == 'CombineGroup') {
            createCombineGroup(item).prependTo($slist);
        }
    });
}

function createStreamBefore(stream) {
    console.log(stream.items.length);
    console.log(stream);
    var $slist = $('.slist');
    $.each(stream.items, function(idx, item){
        if (item.type == 'TweetCard') {
            createTweetCard(item).appendTo($slist);
        }
        else if (item.type == 'CombineGroup') {
            createCombineGroup(item).appendTo($slist);
        }
    });
}

function createTweetCard(card) {
    var $tc = $('.proto > .tweet').clone();
    $tc.data('id', card.id);
    
    $tc.find('.avatar').attr(userLinkAttrs(card.authorId))
        .find('img').attr('src', card.avatar ? card.avatar : '/grids/rs/img/1.jpg');
    $tc.find('.author-name').attr(userLinkAttrs(card.authorId)).text(card.authorName);
    
    $tc.find('.content').html(replaceMention(card.content));
    
    $tc.find('a[uid]').mouseenter(launchUcOpener).mouseleave(launchUcCloser);
    
    if (card.origin)
        $tc.find('.origin').replaceWith(createOriginCard(card.origin));
    else
        $tc.find('.origin').remove();
    
    $tc.find('.time').text(showTime(card.time)).attr('href', '#tw/'+card.id);
    var $tags = $tc.find('.tags');
    var tags = card.origin ? card.origin.tags : card.tags;
    if (tags && tags.length > 0) {
        $tags.html('');
        $.each(card.tags, function(idx, tag){
            createTagLabel(tag).appendTo($tags);
        });
    }
    else {
        $tags.remove();
    }
    
    if (card.forwardCount > 0) {
        $tc.find('.forward .count').text('('+card.forwardCount+')');
    }
    if (card.commentCount > 0) {
        $tc.find('.comment .count').text('('+card.commentCount+')');
    }
    $tc.find('.forward').attr('href', 'javascript:void(0);').click(function(){
        event.preventDefault();
        var $dialog = $('<div>').addClass('forward-dialog modal')
            .css({
                width: '435px',
                minHeight: '100px',
                borderRadius: '10px'
            });
        $('<div>').addClass('modal-header').text('转发微博').appendTo($dialog);
        $('<textarea>').addClass('input modal-body').css({width: '400px', height: '100px'}).appendTo($dialog);
        var $footer = $('<div>').addClass('modal-footer').appendTo($dialog);
        $('<button>').addClass('btn btn-primary').text('转发').css({float: 'right'}).appendTo($footer)
            .click(function() {
                $.post('/grids/post/forward', {
                    content: $dialog.find('.input').val(),
                    originId: card.id
                });
                $dialog.modal('hide');
            });
        
        $dialog.appendTo('#container').modal();
        console.log('forward dialog');
    });
    $tc.find('.comment').attr('href', 'javascript:void(0);').click(function(){
        event.preventDefault();
        var clKey = 'comment-list';
        var $cl = $(this).data(clKey);
        console.log($cl);
        if ($cl) {
            $cl.remove();
            $(this).removeData(clKey);
        }
        else {
            $cl = createCommentList(card.id).appendTo($tc);
            $(this).data(clKey, $cl);
        }
    });
    return $tc;
}

function createOriginCard(origin) {
    var $oc = createTweetCard(origin).removeClass('item').addClass('origin');
    $oc.find('.avatar').remove();
    $oc.find('.tags').remove();
    return $oc;
}

function createCombineGroup(group) {
    var $cg = $('.proto > .combine').clone();
    $.each(group.forwards, function(idx, forward){
        createTweetCard(forward).removeClass('item').addClass('forward').appendTo($cg);
    });
    createOriginCard(group.origin).addClass('offset1').appendTo($cg);
    return $cg;
}

function createBlogData(data) {
    var $bd = $('.proto > .blog').clone();

    $bd.find('.avatar').attr(userLinkAttrs(data.authorId))
        .find('img').attr('src', data.author.avatar ? data.author.avatar : '/grids/rs/img/1.jpg');  
    $bd.find('.author-name').attr(userLinkAttrs(data.authorId)).text(data.author.name);
    $bd.find('.title').text(data.title);
    $bd.find('.content').html(data.content);
    $bd.find('.time').text(showTime(data.time)).attr('href', '#bd/'+data.id);

    var $tags = $bd.find('.tags');
    var tags = data.tags;
    if (tags && tags.length > 0) {
        $tags.html('');
        $.each(data.tags, function(idx, tag){
            createTagLabel(tag).appendTo($tags);
        });
    }
    else {
        $tags.remove();
    }

    return $bd;
}

function createCommentList(tweetId) {
    var $cl = $('<div>');
    var $loading = $('<div>').text('评论加载中').appendTo($cl);

    var $list = $('<ul>').appendTo($cl);
    $.get('/grids/read/'+tweetId+'/comments')
    .done(function(resp){
        $.each(function(idx, item){
            var $li = $('<li>').appendTo($list);
            $('<a>').append($('<img>').attr('src', '')).appendTo($li);
            $('<a>').text(item.authorName).appendTo($li);
            $('<p>').text(item.content).appendTo($li);
            $('<a>').text(showTime(item.time));
        });
        $('<div>').text('评论').replaceAll($loading);
    })
    .fail(function(){
        $('<div>').text('评论加载失败').replaceAll($loading);
    });

    return $cl;
}

function userLinkAttrs(id) {
    return {uid: id, href: '/grids/private/'+id};
}

function showTime(time) {
    return new Date(time).toLocaleString();
}

function replaceMention(content) {
    var indexOfAt = content.indexOf('@');
    var indexOfSpace = content.indexOf(' ', indexOfAt);
    var indexOfInnerAt = content.lastIndexOf('@', indexOfSpace-1);
    
    if (indexOfAt >= 0 && indexOfSpace >=0) {
        if (indexOfInnerAt > indexOfAt && indexOfInnerAt < indexOfSpace) {
            indexOfAt = indexOfInnerAt;
        }
        var mention = content.slice(indexOfAt+1, indexOfSpace);
        var indexOfSharp = mention.indexOf('#');
        if (indexOfSharp > 0) {
            var name = mention.slice(0, indexOfSharp);
            var id = mention.slice(indexOfSharp+1, mention.length);
            return content.slice(0, indexOfAt)
                + $('<a>').text('@'+name).attr(userLinkAttrs(id)).outerHTML()
                + replaceMention(content.slice(indexOfSpace, content.length));
        }
        else {
            return content.slice(0, indexOfSpace)
                + replaceMention(content.slice(indexOfSpace, content.length));
        }
    }
    return content;
}