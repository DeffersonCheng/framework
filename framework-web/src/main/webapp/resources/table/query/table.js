(function($) {
	'use strict';
	$.fn.queryGrid = function(options) {
		if (!options) {
			options = {};
		}
		if (!options.queryId) {
			alert("请指定queryId!!!!!");
			return;
		}
		var _grid = new QueryGrid($(this).attr("id"), options);
		_grid._loadGrid();
		return _grid;
	};
	/**
	 * Grid Object
	 */
	var QueryGrid = function(div, options) {
		this._div = div;
		this._options = options;
		return this;
	}
	QueryGrid.prototype._loadGrid = function() {
		this._loadServerData();
		this._initGrid();
		this._parseData();
		this._initPager();
	}
	// 加载服务器端数据
	QueryGrid.prototype._loadServerData = function() {
		var conditions = null;
		if (this._options && this._options.searchForm) {
			conditions = $("#" + this._options.searchForm).serializeArray();
		}
		var obj = {
			queryId : this._options.queryId,
			conditions : conditions,
			pageInfo : this._options._pageInfo,
			sortInfo:this._options._sortInfo
		};
		var path = basePath + "/queryController/queryData";
		var serverData = ajaxPost(path, {
			parm : JSON.stringify(obj)
		}, null);
		this._options._serverData = serverData;

		// 基本信息
		this._options._tableName = serverData.query.tableName;
		this._options._checkbox = serverData.query.checkbox;

		this._options._columns = this._options._serverData.columns;
		this._options._data = this._options._serverData.objList;
		// 分页相关
		this._options._showPage = this._options._serverData.query.showPage;
		this._options._pageSize = this._options._serverData.pageInfo.pageSize;
		this._options._count = this._options._serverData.pageInfo.count;
		this._options._pageNum = this._options._serverData.pageInfo.pageNum;
		this._options._pageCount = this._options._serverData.pageInfo.pageCount;
	}

	QueryGrid.prototype._initGrid = function() {
		var _this=this;
		var columns = this._options._columns;
		var fields = [];
		for (var i = 0; i < columns.length; i++) {
			var field = {
				field : columns[i].key.replace(".","_"),
				title : columns[i].header,
				width : this.fixWidth(columns[i].width),
				align : columns[i].align,
				hidden : columns[i].hidden,
				sortable : columns[i].sortAble
			};
			if (columns[i].callback) {
				field.formatter = eval(columns[i].callback);
			}
			fields.push(field);
		}
		var _options = {singleSelect:true};//默认单选
		for (var f in this._options){
			_options[f]=this._options[f];//拷贝属性
		}
		//排序
		_options.onSortColumn=function (sort, order) {
			if(!_this._options._pageInfo){
				_this._options._pageInfo={};
			}
			_this._options._sortInfo=sort+" "+order;
			_this._options._pageInfo.pageNum = 1;
			_this._loadServerData();
			_this._parseData();
			_this._initPager();
        };
		_options.columns = [ fields ];
		if (this._options._showPage == "true") {
			_options.pagination = true;
		}
		if (this._options._tableName) {
			_options.title = this._options._tableName;
		}
		//单选
//		
//		if(this._options.singleSelect==false){
//			_options.singleSelect=false;
//		}else{
//			_options.singleSelect=true;
//		}
		if(!_options.toolbar){
			_options.toolbar=[];
		}
		//添加
		if(this._options.addFunc){
			if(!_options.toolbar){
				_options.toolbar=['-'];
			}
			var item={
	                id: _this._div+'_btnadd',
	                text: '添加',
	                iconCls: 'icon-add',
	                handler: function(){
	                     eval(_this._options.addFunc());
	                }
			};
			_options.toolbar.push(item);
			_options.toolbar.push('-');
		}
		//修改
		if(this._options.editFunc){
			if(!_options.toolbar){
				_options.toolbar=['-'];
			}
			var item={
					id: _this._div+'_btnupdate',
	                text: '修改',
	                iconCls: 'icon-edit',
	                handler: function(){
	                	 var selecteds=$('#'+ _this._div).datagrid('getSelections');
	                     eval(_this._options.editFunc(selecteds));
	                }
			};
			_options.toolbar.push(item);
			_options.toolbar.push('-');
		}
		//删除
		if(this._options.removeFunc){
			if(!_options.toolbar){
				_options.toolbar=['-'];
			}
			var item={
					id: _this._options+'_btndelete',
	                text: '删除',
	                iconCls: 'icon-cancel',
	                handler: function(){
	                	 var selecteds=_this._easyGrid.datagrid('getSelections');
	                     eval(_this._options.removeFunc(selecteds));
	                }
			};
			_options.toolbar.push(item);
			_options.toolbar.push('-');
		}
		if(this._options.buttons){
			$.merge(_options.toolbar, this._options.buttons);
		}
		// 加载grid
		this._easyGrid = $('#' + this._div).datagrid(_options);
	}

	QueryGrid.prototype._parseData = function() {
		this._options.thisList = [];
		var objs = this._options._data;
		var columns = this._options._columns;
		for (var i = 0; i < objs.length; i++) {
			var json = objs[i];
			var _row = {};
			for (var j = 0; j < columns.length; j++) {
				var colKey=columns[j].key.replace(".","_");
				var hasKey = false;
				for ( var key in json) {
					if (key == colKey) {
						var col = "";
						if (key == colKey) {
							col = json[key];
						}
						_row[colKey] = col;
						hasKey = true;
						break;
					}
				}
				if (!hasKey) {
					if (colKey == "rowIndex") {
						_row[colKey] = (((this._options._pageNum - 1) * this._options._pageSize)
								+ i + 1);
					} else {
						_row[colKey] = json[colKey];
					}
				}
			}
			this._options.thisList.push(_row);
		}
		this._easyGrid.datagrid("loadData", this._options.thisList);
		if ((typeof this._options.onSearchSucceed)=="function") {
			this._options.onSearchSucceed();
		}
	}

	QueryGrid.prototype._initPager = function() {
		var _this=this;
		// 设置分页组件
		if (_this._options._serverData.query.showPage == "true") {
			_this._page = _this._easyGrid.datagrid('getPager').pagination({
				total : _this._options._count,
				pageSize : _this._options._pageSize,
				pageNumber : _this._options._pageNum,
				pageList : _this._options._serverData.query.pageList
			});
			_this._page.pagination({
				onSelectPage : function(pageNumber, pageSize) {
					_this.onSelectPage(pageNumber, pageSize);
				}
			});
		}
	}
	QueryGrid.prototype.onSelectPage = function(pageNumber, pageSize) {
		if (this._options._pageInfo == null) {
			this._options._pageInfo = {};
		}
		this._options._pageInfo.pageNum = pageNumber;
		this._options._pageInfo.pageSize = pageSize;

		this._loadServerData(this);
		this._parseData(this);
		this._initPager(this);
	}
	QueryGrid.prototype.onSearch = function() {
		if(!this._options._pageInfo){
			this._options._pageInfo={};
		}
		this._options._pageInfo.pageNum = 1;
		this._loadServerData();
		this._parseData();
		this._initPager();
	}
	QueryGrid.prototype.fixWidth = function(percent) {
		return $('#' + this._div).width() * percent;
	}
})(jQuery);