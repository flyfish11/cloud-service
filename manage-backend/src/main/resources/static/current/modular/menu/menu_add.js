var zNodes;
var  aa=function () {
    $.ajax({
        url: domainName + "/api-u/sysMenu/selectZtree/C",
        datatype: JSON,
        async: false,
        success: function (result) {
            zNodes= result;
        },
    });
}
aa();
$(document).ready(function() {
    initSelectTree("pId", false);
    $(".layui-nav-item a").bind("click", function() {
        if (!$(this).parent().hasClass("layui-nav-itemed") && !$(this).parent().parent().hasClass("layui-nav-child")) {
            $(".layui-nav-tree").find(".layui-nav-itemed").removeClass("layui-nav-itemed")
        }
    })
});

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
            url : domainName + '/api-u/sysMenu',
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(data.field),
            success : function(result) {
                /*layer.msg("成功", {shift: -1, time: 1000}, function(){
                });*/
                console.log(result)
            }
        });
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        parent.layer.close(index);
    });
});