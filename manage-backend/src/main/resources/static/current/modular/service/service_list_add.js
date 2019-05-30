layui.use(['layer', 'form', 'table', 'admin', 'ztree', 'laydate', 'upload'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;
    var upload = layui.upload;

    /**
     * 服务管理
     */
    var ServiceListAdd = {
        tableId: "ServiceListAddTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    ServiceListAdd.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: '应用id'},
            {field: 'name', sort: true, templet: '#name', title: '服务名称'},
            {field: 'shortName', sort: true, templet: '#shortName', title: '服务简称'},
            {field: 'serviceGroup', sort: true, templet: '#serviceGroup', title: '服务分组'},
            {field: 'createBy', sort: true, templet: '#createBy', title: '创建人'},
            {field: 'createTime', sort: true, templet: '#createTime', title: '创建时间'},
            {field: 'jarAddr', sort: true, templet: '#createTime', title: 'jar包地址'},
            {field: 'type', sort: true, templet: '#createTime', title: '项目类型'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 250}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ServiceListAdd.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        table.reload(ServiceListAdd.tableId, {where: queryData});
    };

    /**
     * 弹出添加服务对话框
     */
    ServiceListAdd.openAddService = function () {
        var applicationId = getUrlParam("applicationId");
        admin.putTempData('formOk', false);
        admin.open({
            title: "服务创建",
            type: 2,
            area: ['40%', '60%'],
            maxmin: true,
            shadeClose: true,
            content: ['service_add.html?applicationId=' + applicationId],
            end: function () {
                admin.getTempData('formOk') && table.reload(ServiceListAdd.tableId);
            }
        });
    };

    /**
     * 点击服务配置
     *
     */
    ServiceListAdd.serviceConfig = function (data) {
        // layer.open({
        //     title: "服务配置",
        //     type: 2,
        //     area: ['100%', '100%'], //宽高
        //     content: ['service_config.html?id=' + data.id + '&applicationId=' + getUrlParam("applicationId")]
        // });
        add_lay_tab('服务配置', 'pages/service/service_config.html?id=' + data.id + '&applicationId=' + getUrlParam("applicationId")+"&type="+data.type);
    };

    ServiceListAdd.uploadJar = function (data) {
        layer.open({
            type: 2,
            title: 'jar包上传',
            area: ['600px', '600px'],
            content: ['wxcpFile_add.html'],
            btn: ['<i class="fa fa-check"></i> 确认', '<i class="fa fa-close"></i> 关闭'],
            yes: function (index, layero) {
                callBack(index, layero ,data.id);
            }, cancel: function () {
                return true;
            },
            end: function () {
                location.reload();

            }
        });
    };

    //获取选中的节点id
    function callBack(index ,layero,serviceId){
        var iframeWin = window[layero.find('iframe')[0]['name']];
        var url = iframeWin.url;
        console.log(url);
        console.log(serviceId);
        //把jar包地址和服务关联
        $.ajax({
            type : 'put' ,
            url : domainName + '/api-appmanage/service/update',
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify({id:serviceId,jarAddr:url}),
            success : function(data) {
                layer.msg("成功", {shift: -1, time: 1000}, function(){
                    location.reload();
                });
            }
        });

        $.ajax({
            type : 'post' ,
            url : domainName + '/api-appmanage/service/createFile',
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify({id:serviceId,jarAddr:url}),
            success : function(data) {
                layer.msg("成功", {shift: -1, time: 1000}, function(){
                    location.reload();
                });
            }
        });
    }

    /**
     * 点击修改服务
     * @param data
     */
    ServiceListAdd.serviceEdit = function (data) {
        console.log(data)
        admin.putTempData('formOk', false);
        admin.open({
            title: "服务修改",
            type: 2,
            area: ['40%', '60%'],
            maxmin: true,
            shadeClose: true,
            content: ['service_edit.html?applicationId=' + getUrlParam("applicationId") + '&serviceId=' + data.id],
            end: function () {
                admin.getTempData('formOk') && table.reload(ServiceListAdd.tableId);
            }
        });
    };

    /**
     * 服务删除
     * @param data
     */
    ServiceListAdd.serviceDelete = function (data) {
        var operation = function () {
            $.ajax({
                type: "GET",
                url: domainName + "/api-appmanage/service/delete/" + data.id,
                success: function (result) {
                    table.reload(ServiceListAdd.tableId);
                    Feng.success("删除成功!");
                }, error: function (result) {
                    Feng.error(result.msg);
                }
            });
        };
        Feng.confirm("是否删除服务" + data.name + "?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ServiceListAdd.tableId,
        url: domainName + '/api-appmanage/service/list?belongApplication=' + getUrlParam("applicationId"),
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: ServiceListAdd.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ServiceListAdd.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ServiceListAdd.openAddService();
    });

    // 工具条点击事件
    table.on('tool(' + ServiceListAdd.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'serviceEdit') {
            ServiceListAdd.serviceEdit(data);
        }else if (layEvent === 'serviceConfig') {
            ServiceListAdd.serviceConfig(data);
        }else if(layEvent === 'uploadJar') {
            ServiceListAdd.uploadJar(data);
        }else if (layEvent === 'serviceDelete') {
            ServiceListAdd.serviceDelete(data);
        }
    });
});