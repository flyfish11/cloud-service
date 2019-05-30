//初始化表单
$.ajax({
    type: 'get',
    url: domainName + '/api-wf/task/getTaskForm/' + getUrlParam("taskId"),
    contentType: "application/json; charset=utf-8",
    async: false,
    success: function (data) {
        var html = ""
        $.each(data.data, function (index, value) {
            console.log(value);

            if (value.type.type == "enum") {
                html += "<div class=\"layui-form-item\">" +
                    "      <label class=\"layui-form-label\">" + value.name + "</label>" +
                    "      <div class=\"layui-input-block\">" +
                    "           <select name=\"interest\" lay-filter=\"aihao\">" +
                    "                <option value=\"\"></option>";
                $.each(value.type.values, function (index, value) {
                    html += "<option value=\"name \">name</option>";
                })

                html += "           </select>" +
                    "       </div>" +
                    " </div>";
            } else {
                html += "<div class=\"layui-form-item\"> " +
                    "   <label class=\"layui-form-label\">" + value.name + ":</label>" +
                    "   <div class=\"layui-input-block\">" +
                    "        <input type=\"text\" id=\"" + value.id + "\" name=\"" + value.name + "\" placeholder=\"请输入" + value.name + "\" class=\"layui-input\">" +
                    "    </div>" +
                    "</div>";
            }

        });

        $("#completeTaskForm").append(html);
    }
});
layui.use(['element', 'tree', 'layer', 'form', 'upload'], function () {
    var $ = layui.jquery;
});


function update() {
    var formdata = $("#completeTaskForm").serializeObject();
    console.log(formdata);
}

function add() {
    var classifyName = $("#classifyName").val();
    if ($.trim(classifyName) == "") {
        layer.msg('应用名不能为空', {
            icon: 0
        });
        return false;
    }
    var description = $("#description").val();
    if ($.trim(description) == "") {
        layer.msg('描述不能为空', {
            icon: 0
        });
        return false;
    }
    var formdata = $("#addApplicationClassifyForm").serializeObject();
    $.ajax({
        type: 'post',
        url: domainName + '/api-dt/dictionaryTable/add',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(formdata),
        success: function (data) {
            layer.msg("添加成功", {
                shift: -1,
                time: 1000
            }, function () {
                location.href = "applicationClassifyList.html";
            });
        }
    });
}