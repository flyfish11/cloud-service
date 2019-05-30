layui.use(['layer', 'form', 'ztree', 'laydate', 'admin', 'ax', 'table', 'treetable'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var table = layui.table;
    var treetable = layui.treetable;

    /**
     * 系统管理--菜单管理
     */
    var MenuTable = {
        tableId: "menuTable",    //表格id
        condition: {
            menuId: "",
            menuName: "",
            level: ""
        }
    };
    var ApplicationTable = {
        tableId: "applicationTable",
        condition: {
            name: "",
            timeLimit: ""
        }
    };
    ApplicationTable.initColumn = function () {
        return [[
            {field: 'applicationName', title: '应用名称'},
        ]];
    };



    // 渲染应用表格
    var tableResult = table.render({
        elem: '#' + ApplicationTable.tableId,
        url: domainName + '/api-b/application/all',
        page: false,
        height: "full-120",
        cellMinWidth: 100,
        cols: ApplicationTable.initColumn()
    });

    //应用点击事件
    table.on('row('+ApplicationTable.tableId+')', function (obj){
        $("#menuName").val('')
        $("#applicationId").val(obj.data.applicationId);
        MenuTable.search();
        $("#applicationId").val('');
    });

    /**
     * 初始化表格的列
     */
    MenuTable.initColumn = function () {
        return [[
            {type: 'numbers'},
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'name', sort: true, title: '菜单名称' ,minWidth: 200},
            {field: 'pId', sort: true, title: '菜单父编号'},
            {field: 'url', sort: true, title: '请求地址'},
            {field: 'orderNum', sort: true, title: '排序'},
            {field: 'menuType', sort: true, title: '类型' },
            {field: 'visible', sort: true, title: '是否是菜单'},
            {field: 'perms', sort: true, title: '权限标识'},
            {field: 'icon', sort: true, title: '图标'},
            {field: 'createBy', sort: true, title: '创建者'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'updateBy', sort: true, title: '修改人'},
            {field: 'updateTime', sort: true, title: '修改时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击菜单树时
     */
    MenuTable.onClickMenu = function (e, treeId, treeNode) {
        MenuTable.condition.menuId = treeNode.id;
        MenuTable.search();
    };

    /**
     * 点击查询按钮
     */
    MenuTable.search = function () {
        var queryData = {};
        queryData['applicationId'] = $("#applicationId").val();
        queryData['menuName'] = $("#menuName").val();
        queryData['createTime'] = $("#createTime").val();
        MenuTable.initTable(MenuTable.tableId, queryData);
    };

    /**
     * 弹出添加菜单对话框
     */
    MenuTable.openAddMenu = function () {
        layer.open({
            title:"添加菜单",
            type: 2,
            area: ['600px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['menu_add.html']

        });
    };

    /**
     * 导出excel按钮
     */
    MenuTable.exportExcel = function () {
        var checkRows = table.checkStatus(MenuTable.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击编辑菜单按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    MenuTable.onEditMenu = function (data) {
        layer.open({
            title:"修改菜单",
            type: 2,
            area: ['600px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['menu_update.html?id='+data.id]

        });
    };

    /**
     * 点击删除菜单按钮
     *
     * @param data 点击按钮时候的行数据
     */
    MenuTable.onDeleteMenu = function (data) {
        layer.confirm('确认对话框', '你确定删除该选项吗？', function (r) {
            if (r) {
                layer.alert(data.id);
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/api-u/sysMenu/' + data.id,
                    type: "delete",
                    /*beforeSend: function () {
                        index = layer.load();
                    },*/
                    datatype: JSON,
                    success: function (result) {
                        MenuTable.search();
                    },

                });
            }
        });
    };

    /**
     * 初始化表格
     */
    MenuTable.initTable = function (menuId, data) {
        return treetable.render({
            elem: '#' + menuId,
            url: domainName + "/api-u/sysMenu",
            where: data,
            height: "full-140",
            cellMinWidth: 100,
            cols: MenuTable.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'id',
            treePidName: 'pId',
            treeDefaultClose: false,
        });
    };

    // 渲染表格
    var tableResult = MenuTable.initTable(MenuTable.tableId);
    $('#expandAll').click(function () {
        treetable.expandAll('#' + MenuTable.tableId);
    });
    $('#foldAll').click(function () {
        treetable.foldAll('#' + MenuTable.tableId);
    });
/*
    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    //初始化左侧部门树
    var ztree = new $ZTree("menuTree", "/menu/selectMenuTreeList");
    ztree.bindOnClick(Menu.onClickMenu);
    ztree.init();*/

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MenuTable.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MenuTable.openAddMenu();
    });


    // 工具条点击事件
    table.on('tool(' + MenuTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MenuTable.onEditMenu(data);
        } else if (layEvent === 'delete') {
            MenuTable.onDeleteMenu(data);
        } else if (layEvent === 'roleAssign') {
            MenuTable.roleAssign(data);
        } else if (layEvent === 'reset') {
            MenuTable.resetPassword(data);
        }
    });

    // 修改user状态
    form.on('switch(status)', function (obj) {

        var userId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        MenuTable.changeUserStatus(userId, checked);
    });

});
