layui.use(['layer', 'form', 'table','ztree', 'laydate'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;

    /**
     * 系统管理--用户管理
     */
    var TaskTable = {
        tableId: "taskTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    var HistoryTable = {
        tableId: "historyTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

       /**
     * 初始化表格的列
     */
    TaskTable.initColumn = function () {
        return [[
            {field: 'id', title: '任务编码', sort:true},
            {field: 'name', title: '任务名称'},
            {field: 'createTime', title: '创建时间', sort:true, templet:
             "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {field: 'assignee', title: '委派人'},
            {field: 'category', title: '类别',hide:true},
            {field: 'reason', title: '申请原因',hide:true},
            {field: 'description', title: '申请描述',hide:true},
            {field: 'processInstanceId', title: '流程实例id',hide:true},
            {field: 'processDefinitionId', title: '流程定义id',hide:true},
            {align: 'center', toolbar: '#taskListOptions', title: '操作', width: 160}
        ]];
    };

    /**
     * 初始化表格的列
     */
    HistoryTable.initColumn = function () {
        return [[
            {field: 'executionId', title: '任务编码', sort:true},
            {field: 'name', title: '任务名称'},
            {field: 'createTime', title: '创建时间', sort:true, templet:
                    "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {field: 'assignee', title: '委派人'},
            {field: 'category', title: '类别',hide:true},
            {field: 'reason', title: '申请原因',hide:true},
            {field: 'description', title: '申请描述',hide:true},
            {field: 'processInstanceId', title: '流程实例id',hide:true},
            {field: 'processDefinitionId', title: '流程定义id',hide:true},
        ]];
    };

    /**
     * 弹出添加用户对话框
     */
    TaskTable.taskDetails = function (data) {

    };

    /**
     *
     *
     * @param data 点击按钮时候的行数据
     */
    TaskTable.completeTask = function (data) {
        layer.open({
            title:"审核任务",
            type: 2,
            area: ['500px', '400px'],
            maxmin: true,
            shadeClose: true,
            content: ['completeTaskForm.html?taskId=' + data.id],
            end: function () {
                table.reload(TaskTable.tableId);
            }
        });
    };

    // 渲染表格
    var historyTableResult = table.render({
        elem: '#' + HistoryTable.tableId,
        url: domainName + '/api-wf/historyTask',
        page: true,
        height: "full-120",
        cellMinWidth: 100,
        cols: HistoryTable.initColumn()
    });


    // 渲染表格
    var taskTableResult = table.render({
        elem: '#' + TaskTable.tableId,
        url: domainName + '/api-wf/task',
        page: true,
        height: "full-120",
        cellMinWidth: 100,
        cols: TaskTable.initColumn()
    });

    // 工具条点击事件
    table.on('tool(' + TaskTable.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'completeTask') {
            TaskTable.completeTask(data);
        } else if (layEvent === 'taskDetails') {
            TaskTable.taskDetails(data);
        }
    });
});
