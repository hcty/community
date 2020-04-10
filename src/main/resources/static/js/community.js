/**
 * 问题回复
 * @returns {boolean}
 */
function post() {
    var questionId = $("#question_id").val();
    var content=$("#content").val();
    comment2target(questionId,1,content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id=e.getAttribute("data-id");
    var comments=$("#comment-"+id);
    //comments.toggleClass("in");//动态添加移除样式
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        var subCommentContainer=$("#comment-"+id);
        if(subCommentContainer.children().length != 1){
            //已经加载过二级评论了，直接展开二级评论
            comments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (k, comment) {
                    console.log(comment);

                    var mediaLeftElement=$("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        html:comment.user.name
                    })).append($("<div/>",{
                        html:comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    })).append($("<span>",{
                        "class":"pull-right",
                        html:moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                    }));

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElemetn = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElemetn);
                });
                //展开二级评论
                comments.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}
function comment2target(targetId,type,content) {
    if(!content){
        alert("回复内容不能为空！");
        return false;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (data) {
            if(data.code == 200){
                window.location.reload();
            }else{
                if(data.code == 2003){
                    if (confirm(data.message)){
                        window.open("https://github.com/login/oauth/authorize?client_id=dcf0854013085140d0d4&redirect_uri=http://localhost:8888/callback&scope=user&state=1");
                        window.localStorage.setItem("closeable",true);
                    }
                }else{
                    alert(data.message);
                }
            }
            console.log(data);
        },
        dataType: "json"
    });
}
function comment(e) {
    var questionId = e.getAttribute("data-id");
    var content=$("#input-"+questionId).val();
    comment2target(questionId,2,content);
}

function selectTag(tag) {
    var value=tag.getAttribute("data-tag");
    var pervious=$("#tag").val();
    if(pervious.indexOf(value) == -1){
        if(pervious){
            $("#tag").val(pervious+","+value);
        }else{
            $("#tag").val(value);
        }
    }
}

function showSelectTag() {
    $("#selectTag").show();
    $(".tab-pane").first().addClass("active");
}