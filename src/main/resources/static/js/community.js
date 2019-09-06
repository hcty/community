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
                if(data.code == 2003){
                    if (confirm(data.message)){
                        window.open("https://github.com/login/oauth/authorize?client_id=dcf0854013085140d0d4&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
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
    console.log(questionId);
    console.log(content);
}