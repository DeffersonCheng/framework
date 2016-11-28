/**
 * 表单类 只要标注了class="commonForm"的表单均可被此类截获.
 * 用于初始化自定义控件，尤其是控件的数据来源于字典，同时支持自定义数据源,通过src指定
 */
var CommonForm = function() {
	this.formElement = "form[class*='commonForm']";
	this.selectElement = "input[class*='commonSelect']";
	this.radioElement = "input[class*='commonRadio']";
	this.checkboxElement = "input[class*='commonCheckbox']";
	this.dateElement = "input[class*='commonDateField']";
	this.customRadio="input[class*='customRadio']";
	this.customSelect="input[class*='customSelect']";
}
 
/**
 * 初始化所有表单
 */
CommonForm.prototype.initForm = function() {
	// 初始化表单元素
	$form.initFormStyle();
};

/**  
 * 重置表单
 * 
 * @param {Object}
 *            formId
 */
CommonForm.prototype.reset = function(formId) {
	var form = $("#" + formId);
	form[0].reset();
}
/**
 * gather the form data(inputs).
 * 
 * @param {Object}
 *            form
 */

/**
 * 初始化所有表单中的元素（包括select,checkbox,radio,date）
 * 只要是:input[class='pmsSelect/pmsRadio/pmsCheckbox/pmsDateCheck']，均会被该方法初始化
 */
CommonForm.prototype.initFormStyle = function() {
	var form = $(".commonForm");
	form.each(function(index) {
		$form.initSelectElement(this);
		$form.initCheckboxElement(this);
		$form.initRadioElement(this);
		$form.initDateElement(this);
		$form.initCustomRadio(this);
		$form.initCustomSelect(this);
		});
}
/**
 * 初始化select元素
 * 
 * @param {Object}
 *            form
 */
CommonForm.prototype.initSelectElement = function(form) {
	var elements = $(form).find(":" + this.selectElement);
	$(elements).each(function(i) {
		var src = $(this).attr("src");
		var autoLoad = $(this).attr("autoload") == 'false' ? false : true;
		if (src && src.length > 0) {
			if (autoLoad) {
				$form.buildAjaxSelect(this, src);
			} else {
				var _that = this;
				$(this).click(function() {
					$form.buildAjaxSelect(_that, src);
				});
			}
		}
	});
}
/**
 * 初始化radio元素
 * 
 * @param {Object}
 *            form
 */
CommonForm.prototype.initRadioElement = function(form) {
	var elements = $(form).find(":" + this.radioElement);
	$(elements).each(function(i) {
		var src = $(this).attr("src");
		if (src && src.length > 0) {
			$form.buildAjaxRadio(this, src);
		} else {
			$(this).replaceWith("<input type='radio'");
		}
	});
}
/**
 * 初始化checkbox元素
 * 
 * @param {Object}
 *            form
 */
CommonForm.prototype.initCheckboxElement = function(form) {
	var elements = $(form).find(":" + this.checkboxElement);
	$(elements).each(function(i) {
		var src = $(this).attr("src");
		if (src && src.length > 0) {
			$form.buildAjaxCheckbox(this, src);
		} else {
			$(this).replaceWith("<input type='checkbox'");
		}
	});
}
 /**
	 * 初始化date选择器元素
	 * 
	 * @param {Object}
	 *            form
	 */
 CommonForm.prototype.initDateElement = function(form) {
	 var elements = $(form).find(":" + this.dateElement);
	 var iconpath = "/dhtmlx/dhtmlxScheduler/codebase/imgs/calendar.gif";
	 $(elements).each(function(i) {
		 //样式
		 $(this).attr("class","dhxCalendar");
		 var inputId=$(this).attr("id");
		 if(inputId==undefined||inputId==null){
			 inputId=$(this).attr("name");
			 $(this).attr("id",inputId); 
		 }
		 var imageId=inputId+"ImageId";
		 //格式化日期，如果是年则：yyyy,如果是年月日以"2012-02-02显示"，则配置"yyyy-mm-dd",如果是以"2012/02/02"显示，则配置“yyyy/mm/dd”
		 var dateFormat=$(this).attr("format"); 
		 //拼接小按钮
		 $(this).after(" <span><img id='"+imageId+"' class='dhxImage' src='/dhtmlx/dhtmlxScheduler/codebase/imgs/calendar.gif'> </span>");
		 //将文本与小按钮绑定 
		 eval("var "+inputId+"_DateObj=new dhtmlXCalendarObject({input : '"+inputId+"',button : '"+imageId+"'});");
		 eval(inputId+"_DateObj").hideTime(); 
		 //设定时间区间	
		 /*
		 var startDate=null,endDate=null;
		 var startDate=$(this).attr("startDate")==undefined?null:$(this).attr("startDate");
		 var endDate=$(this).attr("endDate")==undefined?null:$(this).attr("endDate");
		 if(startDate!=null||endDate!=null)
		 eval(inputId+"_DateObj").setSensitiveRange(startDate,endDate);
		 */
		 if(dateFormat=="yyyy"){
			 eval(inputId+"_DateObj").setDateFormat("%Y"); 
		 }else if(dateFormat=="yyyy/mm/dd"){
			 eval(inputId+"_DateObj").setDateFormat("%Y/%m/%d");
		 }else if(dateFormat=="yyyy-mm-dd"){
			 //设置日期格式
			 eval(inputId+"_DateObj").setDateFormat("%Y-%m-%d");
		 }else if(dateFormat=="yyyy.mm.dd"){
			 //设置日期格式
			 eval(inputId+"_DateObj").setDateFormat("%Y.%m.%d");
		 }
		 else if(dateFormat=="yyyy.mm"){  
			 //设置日期格式
			 eval(inputId+"_DateObj").setDateFormat("%Y.%m");
		 }
		 else if(dateFormat=="mm"){
			 eval(inputId+"_DateObj").setDateFormat("%m");
		 }		 
		 else{
			 //默认日期格式 
			 eval(inputId+"_DateObj").setDateFormat("%Y-%m-%d");
		 }
	 });
 	};
 /**
	 * 构建ajax方式的select
	 * 
	 * @param {Object}
	 *            selector
	 * @param {Object}
	 *            dictName
	 */
CommonForm.prototype.buildAjaxSelect = function(selector, dictName) {
	var sel = $(selector);
	if (sel.children().length > 0) {
		return false;
	}
	var generateBlankOption = sel.attr('showblank') == 'false' ? false : true;
	var blankValue=sel.attr('blankvalue');
	var blankText=sel.attr('blanktext');
	var build = function(data) {
		if (generateBlankOption == true){ 
			if((blankValue==undefined||blankValue==null)&&(blankText==undefined||blankText==null))
				sel.append($('<option></option>'));
			else if(blankText==undefined||blankText==null)
				sel.append($("<option value='"+blankValue+"'></option>"));
			else if(blankValue==undefined||blankValue==null)
				sel.append($("<option>"+blankText+"</option>"));
			else
				sel.append($("<option value='"+blankValue+"'>"+blankText+"</option>"));
		}
		var data = data;
		if (data && data.length > 0) {
			for ( var i = 0; i < data.length; i++) {
				var option = $("<option value='" + data[i].id + "'>"
						+ data[i].name + "</option>");
				sel.append(option);
			}
		}
	}
	$dict.getDict(dictName, build);
}  

/**
 * 构建ajax方式的radio
 * 
 * @param {Object}
 *            radio
 * @param {Object}
 *            dictName
 */
CommonForm.prototype.buildAjaxRadio = function(radio, dictName) {
	var rd = $(radio);
	var onClick = rd.attr('clicked');
	var validate=rd.attr('validate');  
	var repeat=rd.attr('repeat');	 
	var build = function(data) {
		var dicts = data;
		if (dicts && dicts.length > 0) {
			for ( var i = 0; i < dicts.length; i++) {
				var sb;    
				if(repeat&&i>0&&i%parseInt(repeat)==0){
					sb = "<br/><input type='radio' name='" + rd.attr("name") + "'";
					sb += " value='" + dicts[i].id + "'>" + dicts[i].name
							+ "</input>";  
				}
				else{
					sb = "<input type='radio' name='" + rd.attr("name") + "'";
					sb += " value='" + dicts[i].id + "'>" + dicts[i].name
						+ "</input>";
				}
				var radio = $(sb);
				rd.before(radio);
				if(validate) 
					radio.attr("validate",validate);
				if (onClick) {
					radio.click(function() {
						return eval(onClick + '(this)');
					});
				}
			}
			rd.remove();
		}
	}
	$dict.getDict(dictName, build);
}
/**
 * 构建ajax方式的checkbox
 * 
 * @param {Object}
 *            checkbox
 * @param {Object}
 *            dictName
 */
CommonForm.prototype.buildAjaxCheckbox = function(checkbox, dictName) {
	var ck = $(checkbox);
	var onClick = ck.attr('clicked');
	var build = function(data) {
		var dicts = data;
		for ( var i = 0; i < dicts.length; i++) {
			var checkbox = $("<input type='checkbox' name='" + ck.attr("name")
					+ "' value='" + dicts[i].id + "'>" + dicts[i].name
					+ "</input>");
			ck.before(checkbox);
			if (onClick) {
				checkbox.click(function() {
					return eval(onClick + '(this)');
				});
			}
		}
		ck.remove();
	}
	$dict.getDict(dictName, build);
}
// // CommonForm global instance.

/**
 * 江日念扩展 2014-1-9
 * 初始化自定义radio元素
 * 
 * @param {Object}
 *            form
 */
CommonForm.prototype.initCustomRadio = function(form) {
	var elements = $(form).find(":" + this.customRadio);
	$(elements).each(function(i) {
		var src = $(this).attr("src");
		if (src && src.length > 0) {
			$form.buildCustomAjaxRadio(this, src);
		} else {
			$(this).replaceWith("<input type='radio'");
		}
	});
}

/**
 * 江日念扩展 2014-2-19
 * 初始化自定义select元素
 * 
 * @param {Object}  form
 */ 
CommonForm.prototype.initCustomSelect = function(form) {
	var elements = $(form).find(":" + this.customSelect);
	$(elements).each(function(i) {
		var src = $(this).attr("src");
		var autoLoad = $(this).attr("autoload") == 'false' ? false : true;
		if (src && src.length > 0) {
			if (autoLoad) {
				$form.buildCustomAjaxSelect(this, src);
			} else {
				var _that = this;
				$(this).click(function() {
					$form.buildCustomAjaxSelect(_that, src);
				});
			}
		}
	});
}

/**
 * 江日念扩展 2014-1-9
 * 构建自定义ajax方式的Select
 * 
 * @param {Object}   radio
 * @param {Object}   dictName
 */
CommonForm.prototype.buildCustomAjaxSelect = function(selector, dictName) {
	var sel = $(selector); 
	if (sel.children().length > 0) {
		return false;  
	}
	var generateBlankOption = sel.attr('showblank') == 'false' ? false : true;
	var blankValue=sel.attr('blankvalue');
	var blankText=sel.attr('blanktext');
	var build = function(data) {
		if (generateBlankOption == true){ 
			if((blankValue==undefined||blankValue==null)&&(blankText==undefined||blankText==null))
				sel.append($('<option></option>'));
			else if(blankText==undefined||blankText==null)
				sel.append($("<option value='"+blankValue+"'></option>"));
			else if(blankValue==undefined||blankValue==null)
				sel.append($("<option>"+blankText+"</option>"));
			else
				sel.append($("<option value='"+blankValue+"'>"+blankText+"</option>"));
		}
		var data = data;
		if (data && data.length > 0) {
			for ( var i = 0; i < data.length; i++) {
				var option = $("<option value='" + data[i].id + "'>"
						+ data[i].name + "</option>");
				sel.append(option);
			}
		}
	}
	$dict.getDataByUrl(dictName, build);
}

/**
 * 江日念扩展 2014-1-9
 * 构建自定义ajax方式的radio
 * 
 * @param {Object}
 *            radio
 * @param {Object}
 *            dictName
 */
CommonForm.prototype.buildCustomAjaxRadio = function(radio, dictName) {
	var rd = $(radio);
	var onClick = rd.attr('clicked');
	var repeat=rd.attr('repeat');	
	var build = function(data) {
		var dicts = data;
		if (dicts && dicts.length > 0) {
			for ( var i = 0; i < dicts.length; i++) {
				var sb;
				if(repeat&&i>0&&i%parseInt(repeat)==0){
					sb = "<br/><input type='radio' name='" + rd.attr("name") + "'";
					sb += " value='" + dicts[i].id + "'>" + dicts[i].name
						+ "</input>";
				}
				else
				{    
					sb = "<input type='radio' name='" + rd.attr("name") + "'";
					sb += " value='" + dicts[i].id + "'>" + dicts[i].name
						+ "</input>";
				}
				var radio = $(sb);
				rd.before(radio);
				if (onClick) {
					radio.click(function() {
						return eval(onClick + '(this)');
					});
				}
			}
			rd.remove();
		}
	}
	$dict.getDataByUrl(dictName, build);
}
var $form = commonForm = new CommonForm();
commonForm.initForm();
