
//初始化表单
layui.use(['element', 'tree', 'layer', 'form', 'upload', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var layDate = layui.laydate;

    var start = layDate.render({
        elem: '#startTime',
        type: 'datetime',
        format:'yyyy/MM/dd HH:mm:ss'
    });
    var end = layDate.render({
        elem: '#endTime',
        type: 'datetime',
        format:'yyyy/MM/dd HH:mm:ss'
    });

    function startProcess (formData) {
        var postData = JSON.stringify(formData);
        $.ajax({
            type: 'post',
            url: domainName + '/api-wf/startProcess/'+getUrlParam("processDefinitionId"),
            contentType: 'application/json; charset=utf-8',
            data: postData,
            success: function () {
                layer.msg("流程启动成功", {
                    shift: -1,
                    time: 1000
                }, function () {
                    var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
                    parent.layer.close(index);
                });
            }
        });
    }

    form.on('submit(btnSubmit)', function(data){
        var formData = data.field;
       /* formData["formInfoId"] = "";
        formData["type"] = "";*/
        startProcess(formData);
        console.log(formData)
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});