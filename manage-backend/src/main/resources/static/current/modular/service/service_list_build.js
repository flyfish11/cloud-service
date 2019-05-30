layui.use(['layer', 'form', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var laydate = layui.laydate;
    var layer = layui.layer;
    //获取用户信息
    $.ajax({
        type: 'post',
        url: domainName + '/api-appmanage/service/load?id=' + getUrlParam("id"),
        async: false,
        success: function (data) {
            console.log(data.data)
            form.val('serviceInfo', data.data);
        }
    });

    // 搜索按钮点击事件
    $('#serviceBuild').click(function () {
        layer.load();
        var addr=$("#jarAddr").val();
        $.ajax({
            url: "http://127.0.0.1:8091/build",
            type: "post",
            data: {addr:addr},
            async: true,
            timeout : 0, //超时时间设置，单位毫秒
            success: function (result) {
                layer.closeAll('loading');
                $("#buildLog").val(result);
               /* if (result.success) {
                    layer.msg("构建成功")
                }else {
                    layer.msg("构建失败");
                }
                /!*  window.location.reload();*!/*/
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                layer.closeAll('loading');
               /* alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);*/
                console.log(XMLHttpRequest);
                console.log(textStatus)
                console.log(errorThrown)
                if(errorThrown=="timeout"){
                    layer.msg("构建超时！！！", {icon: 5}, function(){
                        location.reload();
                    });
                }else{
                    layer.msg("构建异常"+errorThrown+" ！！！", {icon: 5}, function(){
                        location.reload();
                    });
                }

            }


        })
    });


});
