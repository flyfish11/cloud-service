
//初始化表单
layui.use([ 'layer', 'form'], function () {
    var $ = layui.jquery;
    var form = layui.form;

    function completeTask (formData) {
        var postData = JSON.stringify(formData);
        console.log(formData);
        $.ajax({
            type: 'post',
            url: domainName + '/api-wf/completeTask',
            contentType: 'application/json; charset=utf-8',
            data: postData,
            success: function () {
                layer.msg("任务已完成", {
                    shift: -1,
                    time: 1000
                }, function () {
                    var index = parent.layer.getFrameIndex(window.name); //获取当前窗口的name
                    parent.layer.close(index);
                });
            }
        });
    }

    form.on('submit(btnSubmit)', function(data){
        var formData = data.field;
        formData["taskId"] = getUrlParam("taskId");
        //formData["type"] = "";
        completeTask(formData);
        console.log(formData)
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});