layui.use(['layer', 'form', 'table','laydate'], function() {
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var table = layui.table;
    var treetable = layui.treetable;

    /**
     *  日志表对象
     * @type {{condition: {flag: string, module: string, startTime: string, endTime: string, username: string}, tableId: string}}
     */
    var MgrLog = {
        tableId: "logTable",    //表格id
        condition: {
            username: "",
            module: "",
            createTime: "",
            flag:"",
            params:"",
            remark:""
        }
    };

    /**
     * 初始化表格的列
     */
    MgrLog.initColumn = function () {
        //var button = "<div><i style='background-color: #1e9fff'></i>&nbsp;显示参数&nbsp;</div>";
        return [[
            {field: 'username', sort: true, title: '用户名'},
            {field: 'module', sort: true, title: '模块'},
            {field: 'createTime', sort: true, title: '创建时间',templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {field: 'remark', sort: false, title: '备注'},
            {field: 'flag', sort: false, title: '状态', templet: "#flagFormat"},
            {field: 'params', sort: false, hide: true ,title: '参数'},
            {align:'center',toolbar: '#tableBar',sort: false, title: '操作'}
        ]]
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MgrLog.tableId,
        url: domainName+ '/api-l/logs',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: MgrLog.initColumn()
    });

    /**
     * 点击查询按钮
     */
    $("#btnSearch").click(
        function () {
            var queryData = {};
            queryData['username'] = $("#username").val();
            queryData['module'] = $("#module").val();
            queryData['flag'] = $("#flag").val();
            queryData['beginTime'] = $("#beginTime").val();
            queryData['endTime'] = $("#endTime").val();
            table.reload(MgrLog.tableId, {where: queryData});
        }
    );

    /**
     * 变量显示弹出
     */
    table.on('tool('+MgrLog.tableId+')', function(obj){
        var data = obj.data;
        var param = data.params;
        var username = data.username;
        var module = data.module;
        var createTime = layui.util.toDateString(data.createTime, 'yyyy-MM-dd HH:mm:ss');
        if (param == "" || param == null) {
            param = "参数为空或参数为加密状态";
        }
        layer.open({
            title :'当前条目详情',
            area: ['400px', '400px'],
            content: "\t<div class = 'layui-card'>\n" +
                "\t\t<div class = 'layui-card-body'>相关用户 : "+username+"</div>\n" +
                "\t\t<div class = 'layui-card-body'>模块  : "+module+"</div>\n" +
                "\t\t<div class = 'layui-card-body'>记录时间  : "+createTime+"</div>\n" +
                "\t\t<div class = 'layui-card-body'>参数内容  : "+param+"<div></div></div>\n" +
                "\t</div>",
            scrollbar: false
        });
    });

    /**
     * 加载select(module)
     */
    $(function () {
        $.ajax({
            type : 'get',
            url : domainName + '/api-l/logs-modules',
            success : function(data) {
                $.each(data, function(key, value){
                    var option = $("<option></option>");
                    option.attr("value", key);
                    option.text(value);
                    $("#module").append(option);
                });
                form.render('select');
            }
        });
    });
});
