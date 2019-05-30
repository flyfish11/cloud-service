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
    var blackTable = {
        tableId: "ipTable",    //表格id
        condition: {
            ip: "",
            level: ""
        }
    };

    /**
     * 初始化表格的列
     */
    blackTable.initColumn = function () {
        return [[
            {field: 'ip', sort: false, title: 'IP地址'},
            {field: 'createTime', sort: false, title: '创建日期',templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + blackTable.tableId,
        url: domainName+ '/api-b/blackIPs',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: blackTable.initColumn()
    });

    /**
     * 点击查询按钮
     */
    blackTable.search = function () {
        var queryData = {};
        queryData['ip'] = $("#ip").val();
        table.reload(blackTable.tableId, {where: queryData});
    };
    /**
     * 弹出添加菜单对话框
     */
    blackTable.openAddMenu = function () {
        layer.open({
            title:"添加IP",
            type: 2,
            area: ['600px', '230px'],
            maxmin: true,
            shadeClose: true,
            content: ['addBlackIP.html'],
            end: function () {
                location.reload();
            }
        });
    };



    /**
     * 点击删除菜单按钮
     *
     * @param data 点击按钮时候的行数据
     */
    blackTable.onDeleteMenu = function (data) {
        layer.confirm( '确定删除该选项吗？', function (r) {
            if (r) {
              //  layer.alert(data.id);
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/api-b/blackIPs/' + data.ip,
                    type: "delete",
                    /*beforeSend: function () {
                        index = layer.load();
                    },*/
                    datatype: JSON,
                    success: function (result) {
                   //     var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
                       // parent.layer.close(index);
                        layer.close(1);
                      //  parent.layer.close(parent.layer.index);
                        location.reload();
                    },

                });
            }
        });
    };
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        blackTable.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        blackTable.openAddMenu();
    });


    // 工具条点击事件
    table.on('tool(' + blackTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            blackTable.onEditMenu(data);
        } else if (layEvent === 'delete') {
            blackTable.onDeleteMenu(data);
        }
    });



});
