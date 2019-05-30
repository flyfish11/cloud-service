layui.use(['layer', 'form', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var laydate = layui.laydate;
    var layer = layui.layer;
    //获取用户信息
    $.ajax({
        type : 'get',
        url : domainName + '/api-b/dictionaries/dictionarieEntity/' +getUrlParam("id"),
        async : false,
        success : function(data) {
            $("#dictName").val(data.name);
            $("#dictCode").val(data.code);
            $("#dictTips").val(data.tips);

            form.val('menuForm', data.date);
            $.ajax({
                type:'get',
                url:domainName + '/api-b/dictionaries/byPidDictionarieEntity/' +getUrlParam("id"),
                async : false,
                success : function(data) {
                    var str="";
                    $.each(data, function (index, val) {
                        str+='<ul style="margin:10px" class="dictItem" name="dictItem"  >值：<input type="int" style="display: inline ; height: 30px; line-height: 30px; border-width: 1px; border-style: solid; background-color: #fff; border-radius: 2px; border-color: #e6e6e6; margin-right: 27px;"  value="'+val.code+'" class="itemCode" />名称：<input type="text" value="'+val.name+'" style="display: inline ; height: 30px; line-height: 30px; border-width: 1px; border-style: solid; background-color: #fff; border-radius: 2px; border-color: #e6e6e6; margin-right: 27px;"  class="itemName"/> 序号：<input type="int" value="'+val.num+'" style="display: inline ; height: 30px; line-height: 30px; border-width: 1px; border-style: solid; background-color: #fff; border-radius: 2px; border-color: #e6e6e6; margin-right: 27px;"  class="itemNum"/></ul>'
                    });
                    $("#Inputs").append(str);
                }
            });
        }
    });

    $(document).ready(function() {
        initSelectTree("pId", false);
        $(".layui-nav-item a").bind("click", function() {
            if (!$(this).parent().hasClass("layui-nav-itemed") && !$(this).parent().parent().hasClass("layui-nav-child")) {
                $(".layui-nav-tree").find(".layui-nav-itemed").removeClass("layui-nav-itemed")
            }
        })
    });
    $(document).on('click','#butCancle',function(){
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        parent.layer.close(index);
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var allDate={};
        var list= [];
        for (var i = 0; i < $(".dictItem").length; i++) {
            var input = $(".dictItem")[i].getElementsByTagName("input");
            for (var j = 0; j < input.length; j++) {
                if(input[j].classList.contains('itemCode')==true){ var itemCode = input[j].value;}
                else if(input[j].classList.contains('itemName')==true){var itemName = input[j].value}
                else if(input[j].classList.contains('itemNum')==true){var itemNum =input[j].value}
            }
            var arr ={"code":itemCode,"name":itemName,"num":itemNum};

            list.push(arr);
        }
        allDate['dictName']=data.field.dictName;
        allDate['dictCode']=data.field.dictCode;
        allDate['dictTips']=data.field.dictTips;
        allDate['id']=getUrlParam("id");
        allDate['list']=list;
        $.ajax({
            type : 'post',
            url : domainName + '/api-b/dictionaries/updateEntity',
            contentType: "application/json; charset=utf-8",
            data : JSON.stringify(allDate),
            success : function(result) {
                layer.msg("成功", {shift: -1, time: 1000}, function() {
                    location.href = "dict_list.html";
                })
            }
        });
        var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
        parent.layer.close(index);
    });
});
