layui.use(['layer', 'form', 'table','ztree', 'laydate'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;

    /**
     * 系统管理--用户管理
     */
    var MgrUser = {
        tableId: "userTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrUser.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: '用户id'},
            {field: 'username', hide: true, sort: true, title: '用户名'},
            {field: 'account', sort: true, title: '账号'},
            {field: 'fullName', sort: true, title: '姓名'},
            {field: 'headImgUrl', sort: true, title: '头像地址'},
            {field: 'phone', sort: true, title: '手机'},
            {field: 'email', sort: true, title: '邮箱'},
            {field: 'idNumber', sort: true, title: '身份证号'},
            {field: 'sex', sort: true, title: '性别'},
            {field: 'type', sort: true, title: '类型'},
            {field: 'deptId', sort: true, title: '部门ID'},
            {field: 'email', sort: true, title: '邮箱'},
            {field: 'phone', sort: true, title: '电话'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'updateTime', sort: true, title: '更新时间'},
            {field: 'status', sort: true, templet: '#statusTpl', title: '状态'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 300}
        ]];
    };

    /**
     * 选择部门时
     */
    MgrUser.onClickDept = function (e, treeId, treeNode) {
        var queryData = {};
        queryData['depId'] = treeNode.id
        table.reload(MgrUser.tableId, {where: queryData});

    };


    /**
     * 点击查询按钮
     */
    MgrUser.search = function () {
        var queryData = {};
        queryData['username'] = $("#username").val();
        queryData['createTime'] = $("#createTime").val();
        queryData['depId'] = $("#depId").val();
        table.reload(MgrUser.tableId, {where: queryData});
    };

   /**
     * 弹出添加用户对话框
     */
    MgrUser.openAddUser = function () {
        layer.open({
            title:"添加用户",
            type: 2,
            area: ['600px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['service_list_add.html']
        });
    };

    /**
     * 导出excel按钮
     */
    MgrUser.exportExcel = function () {
        var checkRows = table.checkStatus(MgrUser.tableId);
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
    MgrUser.onEditUser = function (data) {
        layer.open({
            title:"添加用户",
            type: 2,
            area: ['600px', '600px'],
            maxmin: true,
            shadeClose: true,
            content: ['user_update.html?id='+data.id]
        });
    };

    /**
     * 点击删除用户按钮
     * @param data 点击按钮时候的行数据
     */
    MgrUser.onDeleteUser = function (data) {
        layer.confirm('确认对话框', '你确定删除该选项吗？', function (r) {
            if (r) {
                layer.alert(data.id);
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/user/delete/' + data.id,
                    type: "delete",
                    /*beforeSend: function () {
                        index = layer.load();
                    },*/
                    datatype: JSON,
                    success: function (result) {
                        layer.close(index);
                        table.reload(MgrUser.tableId);
                    },
                    error:function(xhr){
                        console.log(xhr);
                        if (xhr.status == 400) {
                            layer.msg(JSON.parse(msg).message);
                        } else if (xhr.status == 401) {
                            localStorage.removeItem("token");
                            layer.msg("token过期，请重新登录", {shift: -1, time: 1000}, function(){
                                location.href = localStorage.getItem("loginUrl");
                            });

                        } else if (xhr.status == 403) {
                            layer.msg('未授权');
                        } else if (xhr.status == 500) {
                            var info = JSON.parse(msg);
                            var exception = info.exception;
                            var message = info.message;
                            layer.msg('系统错误：' + (exception ? exception : message));
                        }
                    }



                });
            }
        });
    };

    /**
     * 分配角色
     *
     * @param data 点击按钮时候的行数据
     */
    MgrUser.roleAssign = function (data) {
        layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'],
            content:  ['role_assign.html?id='+data.id],
            end: function () {
                table.reload(MgrUser.tableId);
            }
        });
    };

    /**
     * 重置密码
     *
     * @param data 点击按钮时候的行数据
     */
    MgrUser.resetPassword = function (data) {
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
    MgrUser.changeUserStatus = function (userId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/mgr/unfreeze", function (data) {
                Feng.success("解除冻结成功!");
            }, function (data) {
                Feng.error("解除冻结失败!");
                table.reload(MgrUser.tableId);
            });
            ajax.set("userId", userId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/mgr/freeze", function (data) {
                Feng.success("冻结成功!");
            }, function (data) {
                Feng.error("冻结失败!" + data.responseJSON.message + "!");
                table.reload(MgrUser.tableId);
            });
            ajax.set("userId", userId);
            ajax.start();
        }
    };

    // 渲染表格
        var tableResult = table.render({
        elem: '#' + MgrUser.tableId,
        url: domainName+ '/api-u/user/list',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: MgrUser.initColumn()
    });

   /* //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });*/

    //初始化左侧部门树
    var ztree = new $ZTree("deptTree", domainName+ '/api-u/dept/tree');
    ztree.bindOnClick(MgrUser.onClickDept);
    ztree.init();

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MgrUser.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MgrUser.openAddUser();
    });

    // 导出excel
    $('#btnExp').click(function () {
        MgrUser.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MgrUser.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MgrUser.onEditUser(data);
        } else if (layEvent === 'delete') {
            MgrUser.onDeleteUser(data);
        } else if (layEvent === 'roleAssign') {
            MgrUser.roleAssign(data);
        } else if (layEvent === 'reset') {
            MgrUser.resetPassword(data);
        }
    });

    // 修改user状态
    form.on('switch(status)', function (obj) {

        var userId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        MgrUser.changeUserStatus(userId, checked);
    });

});
