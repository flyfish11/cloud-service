layui.use(['layer', 'form', 'table', 'admin', 'ztree', 'laydate'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;

    /**
     * 系统管理--应用管理
     */
    var Application = {
        tableId: "applicationTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Application.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: '项目id'},
            {field: 'name', sort: true, templet: '#name', title: '项目名称'},
            {field: 'short_name', sort: true, templet: '#shortName', title: '项目简称'},
            {field: 'version', sort: true, templet: '#version', title: '版本'},
            {field: 'project_repo_url', sort: true, templet: '#projectRepoUrl', title: '项目地址'},
            {field: 'create_by', sort: true, templet: '#createBy', title: '创建人'},
            {field: 'create_time', sort: true, templet: '#createTime', title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 选择部门时
     */
    Application.onClickDept = function (e, treeId, treeNode) {
        Application.condition.deptId = treeNode.id;
        Application.search();
    };


    /**
     * 点击查询按钮
     */
    Application.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        table.reload(Application.tableId, {where: queryData});
    };

    /**
     * 弹出添加应用对话框
     */
    Application.openAddApplication = function () {
        admin.putTempData('formOk', false);
        layer.open({
            title: "项目创建",
            type: 2,
            area: ['100%', '100%'], //宽高
            content: ['application_add.html'],
            end: function () {
                admin.getTempData('formOk') && table.reload(Application.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Application.exportExcel = function () {
        var checkRows = table.checkStatus(Application.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击服务创建按钮
     *
     */
    Application.onServiceManage = function (data) {
        var type = "gitlab";
        if (data.belongApplication == null) {
            type = "jar";
        }

        admin.putTempData('formOk', false);
        location.href = domainName + '/api-b/pages/service/service_list_add.html?applicationId=' + data.id + "&type=" + type;
        /* add_lay_tab('项目创建', 'pages/service/service_list_add.html?applicationId=' + data.id, "id");*/
        // layer.open({
        //     title: "创建项目",
        //     type: 2,
        //     area: ['100%', '100%'], //宽高
        //     content: ['../service/service_list_add.html?applicationId=' + data.id]
        // });
    };

    /**
     * 应用删除
     * @param data
     */
    Application.applicationDelete = function (data) {
        var operation = function () {
            $.ajax({
                type: "GET",
                url: domainName + "/api-appmanage/application/delete/" + data.id,
                success: function (result) {
                    table.reload(Application.tableId);
                    Feng.success("删除成功!");
                }, error: function (result) {
                    Feng.error(result.responseJSON.message);
                }
            });
        };
        Feng.confirm("是否删除项目" + data.name + "?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Application.tableId,
        url: domainName + '/api-appmanage/application/list',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: Application.initColumn()
    });

    //初始化左侧部门树
    var ztree = new $ZTree("deptTree", domainName + '/api-u/dept/tree');
    ztree.bindOnClick(Application.onClickDept);
    ztree.init();

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Application.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Application.openAddApplication();
    });

    /**
     * 点击修改项目
     * @param data
     */
    Application.applicationEdit = function (data) {
        admin.putTempData('formOk', false);
        admin.open({
            title: "服务修改",
            type: 2,
            area: ['60%', '85%'],
            maxmin: true,
            shadeClose: true,
            content: ['application_edit.html?applicationId=' + data.id],
            end: function () {
                admin.getTempData('formOk') && table.reload(Application.tableId);
            }
        });
    };

    // 导出excel
    $('#btnExp').click(function () {
        Application.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Application.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'applicationEdit') {
            Application.applicationEdit(data);
        }

        if (layEvent === 'serviceManage') {
            Application.onServiceManage(data);
        }
        if (layEvent === 'applicationDelete') {
            Application.applicationDelete(data);
        }
    });
});