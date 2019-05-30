layui.use(['layer', 'form', 'admin', 'ax', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var upload = layui.upload;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //自定义验证规则
    form.verify({
        version: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^\\d+(\\.\\d+)?$").test(value)) {
                return '版本号只能为数字或小数';
            }
        }
    });

    //普通图片上传
    var uploadInst = upload.render({
        elem: '#selectLogo' //绑定元素
        , url: domainName + '/api-f/files/layui?fileSource=LOCAL' //文件上传接口
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                console.log("result=" + result);
                $('#selectLogo').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
            //上传成功
            var fileId = res.data.id;
            $('#logoSrc').val(fileId);
        }
        , error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(domainName + "/api-appmanage/application/add", function (data) {
            Feng.success(data.msg);

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (result) {
            Feng.error(result.responseJSON.message);
        });
        ajax.set(data.field);
        ajax.start();
    });

    // 点击部门时
    $('#belongDepartmentName').click(function () {
        var _title = '选择应用所属部门/单位';
        var _width = 450;
        var _height = $(window).height() - 50;
        layer.open({
            type: 2,
            maxmin: true,
            shade: 0.3,
            title: _title,
            fix: false,
            area: [_width + 'px', _height + 'px'],
            content: ['application_belongDepartment_select.html'],
            shadeClose: true,
            btn: ['<i class="fa fa-check"></i> 确认', '<i class="fa fa-close"></i> 关闭'],
            yes: function (index, layero) {
                belongDepartmentCallBack(index, layero);
            }, cancel: function () {
                return true;
            }
        });
    });

    function belongDepartmentCallBack(index, layero) {
        var body = layer.getChildFrame('body', index);
        if (body.find('#treeId').val() == '') {
            layer.alert("请选择应用所属部门/单位", 5);
            return;
        }
        $("#belongDepartment").val(body.find('#treeId').val());
        $("#belongDepartmentName").val(body.find('#treeName').val());
        $("#factoryId").val(body.find('#factoryId').val());
        layer.close(index);
    };

    // 点击部门时
    $('#authorityAreaName').click(function () {
        var _title = '选择应用使用部门';
        var _width = 450;
        var _height = $(window).height() - 50;
        layer.open({
            type: 2,
            maxmin: true,
            shade: 0.3,
            title: _title,
            fix: false,
            area: [_width + 'px', _height + 'px'],
            content: ['application_useDepts_select.html'],
            shadeClose: true,
            btn: ['<i class="fa fa-check"></i> 确认', '<i class="fa fa-close"></i> 关闭'],
            yes: function (index, layero) {
                applicationUseDeptscallBack(index, layero);
            }, cancel: function () {
                return true;
            }
        });
    });

    function applicationUseDeptscallBack(index, layero) {
        var body = layer.getChildFrame('body', index);
        if (body.find('#treeId').val() == '') {
            layer.alert("请选择应用使用部门", 5);
            return;
        }
        $("#authorityArea").val(body.find('#treeId').val());
        $("#authorityAreaName").val(body.find('#treeName').val());
        $("#factoryId").val(body.find('#factoryId').val());
        layer.close(index);
    };

    //多文件列表示例
    var fileListView = $('#fileList')
        , uploadListIns = upload.render({
        elem: '#selectList'
        , url: domainName + '/api-f/files/layui?fileSource=LOCAL' //文件上传接口
        , accept: 'file'
        , multiple: true
        , auto: false
        , bindAction: '#doListAction'
        , choose: function (obj) {
            var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
            //读取本地文件
            obj.preview(function (index, file, result) {
                var tr = $(['<tr id="upload-' + index + '">'
                    , '<td>' + file.name + '</td>'
                    , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                    , '<td>等待上传</td>'
                    , '<td>'
                    , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                    , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                    , '</td>'
                    , '</tr>'].join(''));

                //单个重传
                tr.find('.demo-reload').on('click', function () {
                    obj.upload(index, file);
                });

                //删除
                tr.find('.demo-delete').on('click', function () {
                    delete files[index]; //删除对应的文件
                    tr.remove();
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                });

                fileListView.append(tr);
            });
        }
        , done: function (res, index, upload) {
            if (res.code == 0) { //上传成功
                var accessoryId = $('#accessoryId').val();
                accessoryId = accessoryId + res.data.id + ",";
                $('#accessoryId').val(accessoryId);

                var tr = fileListView.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                tds.eq(3).html(''); //清空操作
                return delete this.files[index]; //删除文件队列已经上传成功的文件
            }
            this.error(index, upload);
        }
        , error: function (index, upload) {
            var tr = fileListView.find('tr#upload-' + index)
                , tds = tr.children();
            tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
            tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
        }
    });

    loadData();

    function loadData() {
        $.ajax({
                type: "GET",
                url: domainName + "/api-appmanage/application/loadDictData/projectType",
                success: function (data) {
                    var json = data.data;
                    var htmlStr = '';
                    for (var k in json) {
                        htmlStr += '<input type="radio" name="applicationGroup" value="' + k + '" title="' + json[k] + '">';
                    }
                    $('#applicationGroupRadios').html(htmlStr);
                    $("input[name='applicationGroup']").get(0).checked = true;
                    form.render();
                }
                ,
                error: function (result) {
                    Feng.error(result.msg);
                }
            }
        );
    }
});