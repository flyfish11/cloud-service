var Feng = {

};
Feng.info = function (info) {
    top.layer.msg(info, {icon: 6});
};
Feng.success = function (info) {
    top.layer.msg(info, {icon: 1});
};
Feng.error = function (info) {
    top.layer.msg(info, {icon: 2});
};
Feng.confirm = function (tip, ensure) {
    top.layer.confirm(tip, {
        skin: 'layui-layer-admin'
    }, function () {
        ensure();
    });
};

// 以下代码是配置layui扩展模块的目录，每个页面都需要引入
layui.config({
    base:   '../../lib/module/'
}).extend({
    formSelects: 'formSelects/formSelects-v4',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown',
    notice: 'notice/notice',
    step: 'step-lay/step',
    dtree: 'dtree/dtree',
    citypicker: 'city-picker/city-picker',
    tableSelect: 'tableSelect/tableSelect',
    ax: 'ax/ax',
    ztree: 'ztree/ztree-object'
}).use(['admin'], function () {
    var $ = layui.$;
    var admin = layui.admin;

    // 单标签模式需要根据子页面的地址联动侧边栏的选中，用于适配浏览器前进后退按钮
    if (window != top && top.layui && top.layui.myztree && !top.layui.myztree.pageTabs) {
        top.layui.admin.activeNav(location.href.substring(Feng.ctxPath.length));
    }

    // 移除loading动画
    setTimeout(function () {
        admin.removeLoading();
    }, window == top ? 300 : 150);

    //注册session超时的操作
    $.ajaxSetup({
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        complete: function (XMLHttpRequest, textStatus) {

            //通过XMLHttpRequest取得响应头，sessionstatus，
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            if (sessionstatus === "timeout") {

                //如果超时就处理 ，指定要跳转的页面
                window.location = Feng.ctxPath + "/global/sessionError";
            }
        }
    });
});

//在非左侧菜单栏，打开新的tab页
function add_lay_tab(title,url,is_refresh) {
    var id = md5(url);//md5每个url
    parent.element.tabAdd('xbs_tab', {
        title: title
        ,content: '<iframe tab-id="'+id+'" frameborder="0" src="'+url+'" scrolling="yes" class="x-iframe"></iframe>'
        ,id: id
    });
    parent.element.tabChange('xbs_tab', id);
}