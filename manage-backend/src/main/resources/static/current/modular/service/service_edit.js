layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 表单提交事件
    form.on('submit(serviceEditSubmit)', function (data) {
        var ajax = new $ax(domainName + "/api-appmanage/service/edit", function (data) {
            Feng.success(data.msg);

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (result) {
            Feng.error(result.msg);
        });
        ajax.set(data.field);
        ajax.start();
    });

    loadData();

    function loadData() {
        $.ajax({
            type: "POST",
            url: domainName + "/api-appmanage/application/load?id=" + getUrlParam("applicationId"),
            success: function (data) {
                $('#belongApplication').val(data.data.id);
                $('#belongApplicationName').val(data.data.name);
            }, error: function (result) {
                Feng.error(result.msg);
            }
        });

        $.ajax({
                type: "GET",
                url: domainName + "/api-appmanage/application/loadDictData/serviceGroup",
                success: function (data) {
                    var json = data.data;
                    var htmlStr = '';
                    for (var k in json) {
                        htmlStr += '<input type="radio" name="serviceGroup" value="' + k + '" title="' + json[k] + '">';
                    }
                    $('#serviceGroupRadios').html(htmlStr);
                    form.render();
                }
                ,
                error: function (result) {
                    Feng.error(result.msg);
                }
            }
        );

        $.ajax({
            type: "POST",
            url: domainName + "/api-appmanage/service/load?id=" + getUrlParam("serviceId"),
            success: function (data) {
                form.val('serviceForm', data.data);
                var serviceGroup = data.data.serviceGroup;
                console.log("serviceGroup=" + serviceGroup);
                $(":radio[name='serviceGroup'][value='" + serviceGroup + "']").prop("checked", "checked");
                form.render();
            }, error: function (result) {
                Feng.error(result.msg);
            }
        });
    }
});
