/**
 * ztree插件的封装
 */
(function() {

	var $ZTree = function(id, url) {
		this.id = id;
		this.url = url;
		this.onClick = null;
		this.settings = null;
		this.ondblclick=null;
		this.onRightClick=null;
		this.expandAll = true;
	};

	$ZTree.prototype = {
		/**
		 * 初始化ztree的设置
		 */
		initSetting : function() {
			var settings = {
				view : {
					dblClickExpand : true,
					selectedMulti : false,
                    expandAll:true
				},
				data : {simpleData : {enable : true}},
				callback : {
					onClick : this.onClick,
					onDblClick:this.ondblclick,
                    onRightClick:this.onRightClick
					}
			};
			return settings;
		},

		/**
		 * 手动设置ztree的设置
		 */
		setSettings : function(val) {
			this.settings = val;
		},

		/**
		 * 初始化ztree
		 */
		init : function() {
			var zNodeSeting = null;
			if(this.settings != null){
				zNodeSeting = this.settings;
			}else{
				zNodeSeting = this.initSetting();
			}
			var zNodes = this.loadNodes();
			$.fn.zTree.init($("#" + this.id), zNodeSeting, zNodes);
		},

		/**
		 * 绑定onclick事件
		 */
		bindOnClick : function(func) {
			this.onClick = func;
		},
		/**
		 * 右键事件
		 */
		bindOnRightClick : function(func){

			this.onRightClick = func;
		},
		/**
		 * 绑定双击事件
		 */
		bindOnDblClick : function(func) {
			this.ondblclick=func;
		},
        /**
		 * bindOnRightClick
         */
        bindOnRightClick: function(func){
            this.onRightClick=func;
		},
		/**
		 * 加载节点
		 */
		loadNodes : function() {
			var zNodes = null;
			/*var ajax = new $ax(Feng.ctxPath + this.url, function(data) {
				zNodes = data;
			}, function(data) {
				Feng.error("加载ztree信息失败!");
			});
			ajax.start();*/
			$.ajax({
				type : 'get',
				url : this.url,
				async: false,
				contentType: "application/json; charset=utf-8",
				success : function(data) {
					zNodes=data;
					console.log(zNodes)
				}
			});
			return zNodes;
		},

		/**
		 * 获取选中的值
		 */
		getSelectedVal : function(){
			var zTree = $.fn.zTree.getZTreeObj(this.id);
			var nodes = zTree.getSelectedNodes();
			return nodes[0].name;
		}
	};

	window.$ZTree = $ZTree;

}());