function post() {
    var questionId = $("#question_id").val();
    var content=$("#content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (data) {
            if(data.code == 200){
                $("#comment_section").hide();
            }else{
                alert(data.message);
            }
            console.log(data);
        },
        dataType: "json"
    });
    console.log(questionId);
    console.log(content);
}