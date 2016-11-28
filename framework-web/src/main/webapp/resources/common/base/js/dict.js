/*!
 * CommonDict.
 */
var CommonDict=function() {
};
CommonDict.prototype.getDictList = function(code) {
	var dict=null;
	var obj={"code":code};
	//alert(code);    
	$.ajax({
        type: "post",
        async: false,
        url: basePath+"/dict/getDictsByCode",
        data: obj,
        dataType: "json",
        success: function (data) {
			dict=data;
        },
        error: function (err) {
            alert("字典"+code+"获取异常");
        }
    });
	return dict;
};
/**
 * 			dictCode 字典编码
 *          callback 回调函数
 */
CommonDict.prototype.getDict = function(dictCode, callback) {
	if (!dictCode)
		return null;
	var dict = this.getDictList(dictCode);
	if (dict && dict.length > 0) {
		callback(dict);
	}
};
/**
 * 江日念扩展 2014-1-9
 * 自定获取类似字典的数据
 * 
 * **/
CommonDict.prototype.getDataByUrl = function(url, callback) {
	if (!url)
		return null;
	var dict = this.getData(url);
	if (dict && dict.length > 0) {
		callback(dict);
	}
};
/**
 * 江日念扩展 2014-1-9
 * 自定获取类似字典的数据
 * 
 * **/
CommonDict.prototype.getData = function(url) {
	var dict=null;   
	//var obj={"code":code};
	$.ajax({
        type: "post",
        async: false,
        url: basePath+url,
        data: null,
        dataType: "json",
        success: function (data) {
			dict=data;
        },
        error: function (err) {
            alert("获取数据异常");
        }
    });
	return dict;
};


var $dict = new CommonDict();
