layui.use(['layer', 'form', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var laydate = layui.laydate;
    var layer = layui.layer;

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        console.log(data.field)
        $.ajax({
            type : 'post',
            url : domainName + '/api-b/blackIPs',
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(data.field),
            success : function(result) {
                layer.msg("成功", {shift: -1, time: 1000}, function(){
                    location.href = "blackIPList.html";
                });
                console.log(result)
                location.reload();
            }
        });
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        parent.layer.close(index);
    });
});

$(document).on('click','#butCancle',function(){
    var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
    parent.layer.close(index);
});