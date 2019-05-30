layui.use(['layer', 'form', 'table','ztree', 'laydate'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;

    /**
     * 系统管理--用户管理
     */
    var ProcessInstanceTable = {
        tableId: "processInstanceTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    ProcessInstanceTable.initColumn = function () {
        return [[
            {title: '流程实例ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '流程名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '部署ID', field: 'deploymentId', visible: true, align: 'center', valign: 'middle'},
            {title: '流程定义ID', field: 'processDefinitionId', visible: true, align: 'center', valign: 'middle'},
            {title: '用户ID', field: 'tenantId', visible: true, align: 'center', valign: 'middle'},
            {title: '活动ID', field: 'activityId', visible: true, align: 'center', valign: 'middle'},
            {title: '流程定义KEY', field: 'processDefinitionKey', visible: true, align: 'center', valign: 'middle'},
            {title: '流程定义名', field: 'processDefinitionName', visible: true, align: 'center', valign: 'middle'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 300}
        ]];
    };


    /**
     * 点击查询按钮
     */
    ProcessInstanceTable.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        table.reload(ProcessInstanceTable.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    ProcessInstanceTable.createModel = function () {
        $.ajax({
            type: 'GET',
            url: domainName + '/api-wf/act/addModel',
            success: function (data) {
                location.href = "../activiti/modeler.html?modelId=" + data.data;
            }
        });
    };

    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    ProcessInstanceTable.onTrack = function (data) {
        layer.open({
            title:"流程跟踪",
            type: 2,
            area: ['800px', '500px'],
            maxmin: true,
            shadeClose: true,
            content: ['shinePics.html?processInstanceId='+data.id]
        });
    };

    /**
     * 点击删除用户按钮
     * @param data 点击按钮时候的行数据
     */
    ProcessInstanceTable.onDetail = function (data) {
        /*var reData = {};
        reData["id"] = data.id;
        $.ajax({
            url: domainName + '/api-wf/processDetails',
            type: "get",
            data: reData,
            datatype: JSON,
            success: function (data) {
                console.log(data.data);
                layer.msg("成功", {shift: -1, time: 1000}, function(){
                    location.reload();
                });
            },
        });*/
        layer.open({
            title:"流程跟踪",
            type: 2,
            area: ['800px', '500px'],
            maxmin: true,
            shadeClose: true,
            content: ['process_detail.html?processInstanceId='+data.id]
        });
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ProcessInstanceTable.tableId,
        url: domainName + '/api-wf/processInstance',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: ProcessInstanceTable.initColumn()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ProcessInstanceTable.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ProcessInstanceTable.createModel();
    });


    // 工具条点击事件
    table.on('tool(' + ProcessInstanceTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'track') {
            ProcessInstanceTable.onTrack(data);
        } else if (layEvent === 'details') {
            ProcessInstanceTable.onDetail(data);
        }
    });


});
