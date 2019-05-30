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
    var DictTable = {
        tableId: "dictTable",    //表格id

    };
    var tableResult = table.render({
        elem: '#' + DictTable.tableId,
        url: domainName+ "/api-b/dictionaries/list",
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols:  [[
            {field: 'id', hide: true, sort: true, title: 'id'},
            {field: 'name', sort: true, title: '名称'},
            {field: 'datas', sort: true, title: '详情'},
            {field: 'tips', sort: true, title: '备注'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]]
    });
    // 工具条点击事件
    table.on('tool(' + DictTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            DictTable.onEditMenu(data);
        } else if (layEvent === 'delete') {
            DictTable.onDeleteMenu(data);
        }
    });
    /**
     * 点击删除菜单按钮
     *
     * @param data 点击按钮时候的行数据
     */
    DictTable.onDeleteMenu = function (data) {
        layer.confirm('确认', '你确定删除该字典吗？', function (r) {
            if (r) {
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/api-b/dictionaries/deleteEntity/' + data.id,
                    type: "delete",
                    datatype: JSON,
                    success: function (result) {
                        layer.msg("成功", {shift: -1, time: 1000}, function() {
                            location.href = "dict_list.html";
                        })
                    }
                });
            }
        });
    };
   /* /!**
     * 初始化表格的列
     *!/
    DictTable.initColumn = function () {
        return
    };*/

 // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        DictTable.search();
    });
    /**
     * 点击查询按钮
     */
    DictTable.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        table.reload(DictTable.tableId, queryData);
    };
    table.reload= function (menuId, data) {
            return treetable.render({
                elem: '#' + menuId,
                url: domainName + "/api-b/dictionaries/list",
                where: data,
                page: true,
                height: "full-180",
                cellMinWidth: 100,
                treeSpid: 0,
                treeColIndex: 2,
                treePidName: 'pid',
                treeDefaultClose: true,
                treeLinkage: true,
                cols:  [[
                    {field: 'id', hide: true, sort: true, title: 'id'},
                    {field: 'pid', hide: true, sort: true, title: 'pid'},
                    {field: 'name', sort: true, title: '名称'},
                    {field: 'datas', sort: true, title: '详情'},
                    {field: 'tips', sort: true, title: '备注'},
                    {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
                ]]
            });
        };
    /**
     * 弹出添加菜单对话框
     */
    DictTable.openAddMenu = function () {
        layer.open({
            title:"添加菜单",
            type: 2,
            area: ['800px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['dict_add.html']

        });
    };
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        DictTable.openAddMenu();
    });
    /**
     * 点击编辑菜单按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    DictTable.onEditMenu = function (data) {
        layer.open({
            title:"修改字典",
            type: 2,
            area: ['800px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['dict_update.html?id='+data.id]
        });
    };
});
