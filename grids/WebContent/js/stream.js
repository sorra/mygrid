'use strict';

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
    var $slist = $('.slist').empty().warnEmpty();
    $('<a class="newfeed btn">').text('看看新的').css('margin-left', '320px').prependTo($stream)
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

    $('<a class="oldfeed btn">').text('看看更早的').css('margin-left', '320px').appendTo($stream)
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
    if (stream.items.length == 0) {
        tipover($('.stream .newfeed').warnEmpty(), '还没有新的');
    }
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
    if (stream.items.length ==0) {
        tipover($('.stream .oldfeed').warnEmpty(), '没有更早的了');
    }
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
    $tc.find('a.avatar').attr(userLinkAttrs(card.authorId))
        .find('img').attr('src', card.avatar);
    $tc.find('.author-name').attr(userLinkAttrs(card.authorId)).text(card.authorName); 
    var content = card.prefo ? card.content+card.prefo : card.content;
    $tc.find('.content').html(replaceMention(content));
    
    $tc.find('a[uid]').mouseenter(launchUcOpener).mouseleave(launchUcCloser);
    
    if (card.origin)
        $tc.find('.origin').replaceWith(createOriginCard(card.origin));
    else
        $tc.find('.origin').remove();
    
    $tc.find('.time').text(showTime(card.time)).attr('href', webroot+'/twt/'+card.id);
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
    $tc.find('.forward').attr('href', 'javascript:;').click(function(){
        event.preventDefault();
        var $dialog = $('<div class="forward-dialog modal">')
            .css({
                width: '435px',
                minHeight: '100px',
                borderRadius: '10px'
            });
        $('<div class="modal-header">').text('转发微博').appendTo($dialog);
        $('<textarea class="input modal-body">').css({width: '400px', height: '100px'}).appendTo($dialog);
        var $footer = $('<div class="modal-footer">').appendTo($dialog);
        $('<button class="btn btn-primary">').text('转发').css({float: 'right'}).appendTo($footer)
            .click(function() {
                $.post(webroot+'/post/forward', {
                    content: $dialog.find('.input').val(),
                    originId: card.id
                });
                $dialog.modal('hide');
            });
        
        $dialog.appendTo('#container').modal();
        console.log('forward dialog');
    });
    $tc.find('.comment').attr('href', 'javascript:;').click(function(){
        event.preventDefault();
        var clKey = 'comment-list';
        var $cl = $(this).data(clKey);
        if ($cl) {
            $cl.remove();
            $(this).removeData(clKey);
        }
        else {
            $cl = createCommentList(card.id).appendTo($tc.find('.t-part'));
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
        .find('img').attr('src', data.author.avatar ? data.author.avatar : webroot+'/rs/img/1.jpg');  
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
    var $input = $('<input>').appendTo($cl);
    $('<button class="btn btn-small btn-success">').text('评论').appendTo($cl)
        .click(function(){
           $.post(webroot+'/post/comment', {
               content: $input.val(),
               sourceId: tweetId
           });
           $input.val('');
        });
    var $loading = $('<div>').text('评论加载中').appendTo($cl);

    var $list = $('<ul>').appendTo($cl);
    $.get(webroot+'/read/'+tweetId+'/comments')
    .done(function(resp){
        console.log(resp.length);
        $.each(resp, function(idx, item){
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