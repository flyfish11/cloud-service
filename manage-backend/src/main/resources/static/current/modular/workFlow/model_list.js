layui.use(['layer', 'form', 'table','ztree', 'laydate'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;

    /**
     * 系统管理--用户管理
     */
    var ModelTable = {
        tableId: "modelTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    ModelTable.initColumn = function () {
        return [[
            {title: '模型ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '修订版本', field: 'revision', visible: true, align: 'center', valign: 'middle'},
            {title: '模型名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: 'key', field: 'key', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '最后修改时间', field: 'lastUpdateTime', visible: true, align: 'center', valign: 'middle'},
            {title: '版本', field: 'version', visible: true, align: 'center', valign: 'middle'},
            {title: '元信息', field: 'metaInfo', visible: true, align: 'center', valign: 'middle'},
            {title: '编辑器源值ID', field: 'editorSourceValueId', visible: true, align: 'center', valign: 'middle'},
            {title: '编辑器源额外值ID', field: 'editorSourceExtraValueId', visible: true, align: 'center', valign: 'middle'},

            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 300}
        ]];
    };



    /**
     * 点击查询按钮
     */
    ModelTable.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        table.reload(ModelTable.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    ModelTable.createModel = function () {
        $.ajax({
            type: 'GET',
            url: domainName + '/api-wf/act/addModel',
            success: function (data) {
                console.log(data)
                location.href = "../../activiti/modeler.html?modelId=" + data.msg;
            }
        });
    };
    /**
     * 弹出添加用户对话框
     */
    ModelTable.onDeploy = function (data) {
        $.ajax({
            url: domainName + '/api-wf/act/deployActModel/' + data.id,
            type: "post",
            beforeSend: function () {
                index = layer.load();
            },
            datatype: JSON,
            success: function (result) {
                layer.close(index);
                layer.msg("部署成功", {
                    shift: -1,
                    time: 3000
                }, function() {
                    lay.table.reload("ActModelLayui", { //此处是上文提到的 初始化标识id
                    })
                });
            }
        });
    };

    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    ModelTable.onEditModer = function (data) {
        console.log(data)
        location.href = "../../activiti/modeler.html?modelId=" + data.id;
    };

    /**
     * 点击删除用户按钮
     * @param data 点击按钮时候的行数据
     */
    ModelTable.onDeleteModel = function (data) {
        layer.confirm('确认对话框', '你确定删除该选项吗？', function (r) {
            if (r) {
                layer.alert(data.id);
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/api-wf/act/removeActModel/' + data.id,
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
        elem: '#' + ModelTable.tableId,
        url: domainName + '/api-wf/act/queryActModel',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: ModelTable.initColumn()
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ModelTable.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ModelTable.createModel();
    });


    // 工具条点击事件
    table.on('tool(' + ModelTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ModelTable.onEditModer(data);
        } else if (layEvent === 'delete') {
            ModelTable.onDeleteModel(data);
        } else if (layEvent === 'deploy') {
            ModelTable.onDeploy(data);
        }
    });


});
