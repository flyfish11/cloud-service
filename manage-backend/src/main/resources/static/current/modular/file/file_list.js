layui.use(['layer', 'form', 'table','ztree', 'laydate','upload'], function () {
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var laydate = layui.laydate;
    var $ZTree = layui.ztree;
    var upload = layui.upload;

    /**
     * 系统管理--文件中心
     */
    var FileCenter = {
        tableId: "fileTable",    //表格id
        condition: {
            name: "",
            timeLimit: ""
        }
    };
    /**
     * 初始化表格的列
     */
    FileCenter.initColumn = function () {
        return [[
            /*所有的字段名*/
            {type: 'checkbox'},
            {field: 'id', hide: true, sort: true, title: '文件id',minWidth: 50},
            {field: 'name', sort: true,title: '文件名称',minWidth: 50},
            {field: 'url', sort: true,title: 'url',minWidth: 50},
            {field: 'isImg', sort: true,title: '图片',minWidth: 50},
            {field: 'size', sort: true,title: '大小',minWidth: 50},
            {field: 'contentType', sort: true,title: '数据类型',minWidth: 50},
            {field: 'path', sort: true, title: 'path',minWidth: 50},
            {field: 'source', sort: true, title: '文件上传位置',minWidth: 50},
            {field: 'createTime', sort: true,title: '创建时间',minWidth: 50},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 50}
        ]];
    };
    upload.render({
        elem: '#test1' //绑定元素
        ,accept: 'file' //允许上传的文件类型
        ,url: domainName + '/api-f/files?fileSource=' + $("#fileSource").val()
        ,done: function(res, index, upload){
            layer.msg("上传成功");
            window.location.reload();
            example.ajax.reload();
        }
    });
    $("#fileSource").change(function(){
        var url = domainName + '/api-f/files?fileSource=' + $("#fileSource").val();
        $("#test1").attr("lay-data", "{url: '" + url + "'}");
    });
    /**
     * 点击删除文件按钮
     * @param data 点击按钮时候的行数据
     */
    FileCenter.onDeleteFile = function (data) {
        layer.confirm('确认对话框', '你确定删除该选项吗？', function (r) {
            if (r) {
                //layer.alert(data.id);
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/api-f/files/'+data.id,
                    type: "delete",
                    success: function (result) {
                        layer.msg("删除成功");
                        window.location.reload();
                        example.ajax.reload();
                    },

                });
            }
        });
    };
    FileCenter.onDownLoad = function (data) {
        layer.confirm('确认对话框', '你确定删除该选项吗？', function (r) {
            if (r) {
                layer.alert(data.id);
                //发送请求删除一条记录
                //发送ajax请求删除一条数据  删除成功之后 刷新datagrid
                $.ajax({
                    url: domainName + '/api-f/files/download/'+data.id,
                    type: "get",
                    success: function (result) {
                        layer.msg("下载成功");
                        //window.location.reload();
                        example.ajax.reload();
                    },

                });
            }
        });
    };
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + FileCenter.tableId,
        url: domainName+ '/api-f/files/list',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: FileCenter.initColumn()
    });
    // 工具条点击事件
    table.on('tool(' + FileCenter.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'delete') {
            FileCenter.onDeleteFile(data);
        }
        if (layEvent === 'download') {
            FileCenter.onDownLoad(data);
        }
    });

});