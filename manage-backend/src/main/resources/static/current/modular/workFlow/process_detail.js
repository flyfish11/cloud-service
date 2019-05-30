layui.use(['layer', 'form', 'table'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table=layui.table;

    /**
     * 系统管理--用户管理
     */
    var process_detail = {
        tableId: "process_detail",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    process_detail.initColumn = function () {
        return [[
            {title: '审核人id', field: 'opId', visible: true, align: 'center', valign: 'middle'},
            {title: '审核人姓名', field: 'opName', visible: true, align: 'center', valign: 'middle'},
            {title: '是否通过', field: 'flag', visible: true, align: 'center', valign: 'middle'},
            {title: '审核时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}

        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + process_detail.tableId,
        url: domainName + '/api-wf/processDetails/'+getUrlParam("processInstanceId"),
        page: false,
        height: "full-120",
        cellMinWidth: 100,
        cols: process_detail.initColumn()
    });

});
