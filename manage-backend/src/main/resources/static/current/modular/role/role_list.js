layui.use(['layer', 'form', 'table','ztree', 'laydate'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;

    /**
     * 系统管理--用户管理
     */
    var RoleTable = {
        tableId: "roleTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    RoleTable.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: '角色ID'},
            {field: 'name', hide: true, sort: true, title: '角色名'},
            {field: 'code', sort: true, title: '账号'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'updateTime', sort: true, title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 300}
        ]];
    };

    /**
     * 选择部门时
     */
    RoleTable.onClickDept = function (e, treeId, treeNode) {
        RoleTable.condition.deptId = treeNode.id;
        RoleTable.search();
    };


    /**
     * 点击查询按钮
     */
    RoleTable.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        table.reload(RoleTable.tableId, {where: queryData});
    };

    /**
     * 弹出添加用户对话框
     */
    RoleTable.openAddUser = function () {
        layer.open({
            title:"添加",
            type: 2,
            area: ['600px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['role_add.html']

        });
    };
    /**
     * 导出excel按钮
     */
    RoleTable.exportExcel = function () {
        var checkRows = table.checkStatus(RoleTable.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    RoleTable.onEditUser = function (data) {
        layer.open({
            title:"修改角色",
            type: 2,
            area: ['600px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['role_update.html?id='+data.id],
            end: function () {
                table.reload(RoleTable.tableId);

            }
        });
    };

    /**
     * 点击删除用户按钮
     * @param data 点击按钮时候的行数据
     */
    RoleTable.onDeleteUser = function (data) {
        layer.confirm('确认对话框', '你确定删除该选项吗？', function (r) {
            if (r) {
                layer.alert(data.id);
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/api-u/roles/' + data.id,
                    type: "delete",
                    /*beforeSend: function () {
                        index = layer.load();
                    },*/
                    datatype: JSON,
                    success: function (result) {
                        layer.close(index);
                    },

                });
            }
        });
    };



    /**
     * 分配角色
     *
     * @param data 点击按钮时候的行数据
     */
    RoleTable.roleAssign = function (data) {
        layer.open({
            type: 2,
            title: '分配菜单权限',
            area: ['400px', '600px'],
            content: ['role_assign.html?roleId='+data.id],
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
    function callBack(index ,layero,roleId){
        var iframeWin = window[layero.find('iframe')[0]['name']];
        var arrayIds = iframeWin.getMenuIds();
        $.ajax({
            type : 'post',
            url : domainName + '/api-u/sysMenu/toRole/'+ roleId,
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(arrayIds),
            success : function(data) {
                layer.msg("成功", {shift: -1, time: 1000}, function(){
                    location.reload();
                });
            }

        });


    }

    /**
     * 重置密码
     *
     * @param data 点击按钮时候的行数据
     */
    RoleTable.resetPassword = function (data) {
        Feng.confirm("是否重置密码为111111 ?", function () {
            var ajax = new $ax(Feng.ctxPath + "/mgr/reset", function (data) {
                Feng.success("重置密码成功!");
            }, function (data) {
                Feng.error("重置密码失败!");
            });
            ajax.set("userId", data.userId);
            ajax.start();
        });
    };

    /**
     * 修改用户状态
     *
     * @param userId 用户id
     * @param checked 是否选中（true,false），选中就是解锁用户，未选中就是锁定用户
     */
    RoleTable.changeUserStatus = function (userId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/mgr/unfreeze", function (data) {
                Feng.success("解除冻结成功!");
            }, function (data) {
                Feng.error("解除冻结失败!");
                table.reload(RoleTable.tableId);
            });
            ajax.set("userId", userId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/mgr/freeze", function (data) {
                Feng.success("冻结成功!");
            }, function (data) {
                Feng.error("冻结失败!" + data.responseJSON.message + "!");
                table.reload(RoleTable.tableId);
            });
            ajax.set("userId", userId);
            ajax.start();
        }
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + RoleTable.tableId,
        url: domainName+ '/api-u/roles1',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: RoleTable.initColumn()
    });

    /* //渲染时间选择框
     laydate.render({
         elem: '#timeLimit',
         range: true,
         max: Feng.currentDate()
     });*/

   /* //初始化左侧部门树
    var ztree = new $ZTree("deptTree", domainName+ '/api-u/dept/tree');
    ztree.bindOnClick(RoleTable.onClickDept);
    ztree.init();*/

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        RoleTable.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        RoleTable.openAddUser();
    });

    // 导出excel
    $('#btnExp').click(function () {
        RoleTable.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + RoleTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            RoleTable.onEditUser(data);
        } else if (layEvent === 'delete') {
            RoleTable.onDeleteUser(data);
        } else if (layEvent === 'roleAssign') {
            RoleTable.roleAssign(data);
        } else if (layEvent === 'reset') {
            RoleTable.resetPassword(data);
        }
    });

    // 修改user状态
    form.on('switch(status)', function (obj) {

        var userId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        RoleTable.changeUserStatus(userId, checked);
    });

});
