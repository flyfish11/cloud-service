/**
 * 服务构建
 */
var ServiceConfig = {};

/**
 * 点击下一步按钮
 *
 */
ServiceConfig.applicationBuild = function () {
    var serviceId = getUrlParam("id");
    // layer.open({
    //     title: "服务构建",
    //     type: 2,
    //     area: ['100%', '100%'], //宽高
    //     content: ['service_list_build.html?applicationId=' + applicationId]
    // });
    add_lay_tab('服务构建', 'pages/service/service_list_build.html?id=' + serviceId);
};

// 下一步按钮点击事件
$('#nextStep').click(function () {
    ServiceConfig.applicationBuild();
});