layui.use(['layer', 'form',  'admin', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;

    admin.iframeAuto();
    //获取用户信息
    $.ajax({
        type : 'get',
        url : domainName + '/api-u/roles/' +getUrlParam("id"),
        async : false,
        success : function(data) {
            form.val('roleForm', data);
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        $.ajax({
            type : 'put',
            url : domainName + '/api-u/roles',
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(data.field),
            success : function(result) {
                /*layer.msg("成功", {shift: -1, time: 1000}, function(){
                });*/
            }
        });
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        parent.layer.close(index);
    });
});
