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
    var ApplicationAuth = {
        tableId: "applicationAuthTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    ApplicationAuth.initColumn = function () {
        return [[
            {type: 'radio'},
            {field: 'id', hide: true, sort: true, title: '项目id'},
            {field: 'name', sort: true, templet: '#name', title: '项目名称'},
            {field: 'version', sort: true, templet: '#version', title: '版本'},
            {field: 'runState', sort: true, templet: '#runState', title: '状态'},
            {field: 'createBy', sort: true, templet: '#createBy', title: '创建人'},
            {field: 'createTime', sort: true, templet: '#createTime', title: '创建时间'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ApplicationAuth.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        table.reload(ApplicationAuth.tableId, {where: queryData});
    };

    /**
     * 弹出添加应用对话框
     */
    ApplicationAuth.openAddApplicationAuth = function () {
        layer.open({
            title: "项目创建",
            type: 2,
            area: ['100%', '100%'], //宽高
            content: ['ApplicationAuth_add.html']
        });
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ApplicationAuth.tableId,
        url: domainName + '/api-appmanage/application/list',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: ApplicationAuth.initColumn()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ApplicationAuth.search();
    });
});