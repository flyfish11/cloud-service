$(document).ready(function() {

    var MaxInputs       = 8;
    var Inputs   = $("#Inputs");
    var AddButton  = $("#AddMoreFileBox");

    var x = Inputs.length;
    var FieldCount=1;

    $(AddButton).click(function (e)
    {
        if(x <= MaxInputs)
        {
            FieldCount++;
            //add input box
            $(Inputs).append( '<ul style="margin:10px" class="dictItem" name="dictItem" >值：<input type="int" style="display: inline ; height: 30px; line-height: 30px; border-width: 1px; border-style: solid; background-color: #fff; border-radius: 2px; border-color: #e6e6e6; margin-right: 27px;" class="itemCode" lay-verify="required" required/>名称：<input type="text" style="display: inline;  height: 30px; line-height: 30px; border-width: 1px; border-style: solid; background-color: #fff; border-radius: 2px; border-color: #e6e6e6; margin-right: 27px;" class="itemName" lay-verify="required" required/> 序号：<input type="int" style="display: inline; height: 30px; line-height: 30px; border-width: 1px; border-style: solid; background-color: #fff; border-radius: 2px; border-color: #e6e6e6; margin-right: 5px" class="itemNum" lay-verify="required" required/><a href="#"  style=" font-size: 24px;cursor:hand"  class="removeclass">×</a></ul>');

            x++; //text box increment
        }
        return false;
    });
    $("body").on("click",".removeclass", function(e){
        if( x > 1 ) {
            $(this).parent('ul').remove();
            x--;
        }
        return false;
    })

});

$(document).on('click','#butCancle',function(){
    var index=parent.layer.getFrameIndex(window.name); //获取当前窗口的name
    parent.layer.close(index);
});

layui.use(['layer', 'form', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var laydate = layui.laydate;
    var layer = layui.layer;

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
        allDate['list']=list;
        $.ajax({
            type : 'post',
            url : domainName + '/api-b/dictionaries/saveEntity',
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

