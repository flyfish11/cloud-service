layui.use(['layer', 'form', 'table','ztree', 'laydate'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;

    /**
     * 系统管理--用户管理
     */
    var ProcessDefinitionTable = {
        tableId: "processDefinitionTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    ProcessDefinitionTable.initColumn = function () {
        return [[
            {title: '流程ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '类别', field: 'category', visible: true, align: 'center', valign: 'middle'},
            {title: '流程名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: 'key', field: 'key', visible: true, align: 'center', valign: 'middle'},
            {title: '版本', field: 'version', visible: true, align: 'center', valign: 'middle'},
            {title: '资源名称', field: 'resourceName', visible: true, align: 'center', valign: 'middle'},
            {title: '部署ID', field: 'deploymentId', visible: true, align: 'center', valign: 'middle'},
            {title: '图表资源名称', field: 'diagramResourceName', visible: true, align: 'center', valign: 'middle'},
            {title: '开启流程的表单', field: 'hasStartFormKey', visible: true, align: 'center', valign: 'middle'},
            {title: '图形符号', field: 'hasGraphicalNotation', visible: true, align: 'center', valign: 'middle'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 300}
        ]];
    };


    /**
     * 点击查询按钮
     */
    ProcessDefinitionTable.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        table.reload(ProcessDefinitionTable.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    ProcessDefinitionTable.onStartProcess = function (data) {

      /*  location.href = "startProcessForm.html?processDefinition=" + data.id;*/

        layer.open({
            title:"开启流程",
            type: 2,
            area: ['600px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['startProcessForm.html?processDefinitionId=' + data.id]
        });
    };

    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    ProcessDefinitionTable.onEditUser = function (data) {
        location.href = "../activiti/modeler.html?modelId=" + data.data;
    };

    /**
     * 点击删除用户按钮
     * @param data 点击按钮时候的行数据
     */
    ProcessDefinitionTable.onDeleteModel = function (data) {
        layer.confirm('确认对话框', '你确定删除该选项吗？', function (r) {
            if (r) {
                layer.alert(data.id);
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/api-wf/processDefinition/' + data.id,
                    type: "delete",
                    datatype: JSON,
                    success: function (result) {
                        layer.msg("成功", {shift: -1, time: 1000}, function(){
                            location.reload();
                        });

                    },

                });
            }
        });
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ProcessDefinitionTable.tableId,
        url: domainName + '/api-wf/processDefinition',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: ProcessDefinitionTable.initColumn()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ProcessDefinitionTable.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ProcessDefinitionTable.createModel();
    });


    // 工具条点击事件
    table.on('tool(' + ProcessDefinitionTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'start') {
            ProcessDefinitionTable.onStartProcess(data);
        } else if (layEvent === 'delete') {
            ProcessDefinitionTable.onDeleteModel(data);
        }
    });


});
