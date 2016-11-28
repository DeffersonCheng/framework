//全局的ajax访问，处理ajax清求时sesion超时  
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		//通过XMLHttpRequest取得响应头，sessionstatus，  
		var sessionstatus = XMLHttpRequest
				.getResponseHeader("systemStatus");
		if (sessionstatus == "timeout") {
			alert("您登陆时间过长，请重新登陆！");
			//如果超时就处理 ，指定要跳转的页面  
			window.location = basePath + "/userLogin/userLogin";
		} else if (sessionstatus == "csrf") {
			alert("您的当前请求存在安全隐患，请重新登陆！");
			//如果超时就处理 ，指定要跳转的页面  
			window.location = basePath + "/userLogin/userLogin";
		}
	},
	beforeSend : function(XMLHttpRequest) {
		XMLHttpRequest.setRequestHeader("csrfToken", $("#csrfToken").val());
	}
});
function ajaxPost(url, dataParam, callback,errorC) {
	var retData = null;
	$.ajax({
		type : "post",
		async : false,
		url : url,
		data : dataParam,
		dataType : "json",
		success : function(data, status) {
			if(data&&("100"==data.code)){
				alert(data.data);
				return; 
			}
			retData = data;
			if (callback != null && callback != "" && callback != undefined)
				callback(data, status);
		},
		error : function(err, err1, err2) {
			if (typeof errorC =="function") errorC(err,err1,err2);
			var xhr=err;
			var textStatus=err1;
			var response=err2;
			if(textStatus=="timeout"|| xhr.statusText.indexOf("NetworkError")>-1 ){
				alert("网络发生异常，请刷新重试。");
			}else{
				alert("系统发生未知异常，请尝试刷新重试。如果继续出现请联系开发人员。");
			}
		}
	});
	return retData; 
}
function asyncPost(url, dataParam, callback,error) {
	if(!dataParam){
		dataParam={};
	}
	//主要是为了防御csrf攻击
	if(!dataParam.csrfToken){
		dataParam.csrfToken=$("#csrfToken").val();
	}
	var retData = null;
	$.ajax({
		type : "post",
		async : true,
		url : url,
		data : dataParam,
		dataType : "json",
		success : function(data, status) {
			if(data&&("100"==data.code)){
				alert(data.data);
				return; 
			}
			retData = data;
			if (callback != null && callback != "" && callback != undefined)
				callback(data, status);
		},
		error : function(err, err1, err2) {
			if (typeof errorC =="function") errorC(err,err1,err2);
			else{
				var xhr=err;
				var textStatus=err1;
				var response=err2;
				if(textStatus=="timeout"|| xhr.statusText.indexOf("NetworkError")>-1 ){
					alert("网络发生异常，请刷新重试。");
				}else{
					alert("系统发生未知异常，请尝试刷新重试。如果继续出现请联系开发人员。");
				}
			}
		}
	});
	return retData; 
}
function ajaxPostText(url, dataParam, callback) {
	var retData = null;
	$.ajax({
		type : "post",
		async : false,
		url : url,
		data : dataParam,
		dataType : "text",
		success : function(data, status) {
			// alert(data);
			retData = data;
			if (callback != null && callback != "" && callback != undefined)
				callback(data, status);
		},
		error : function(err, err1, err2) {
			alert("调用方法发生异常:" + JSON.stringify(err) + "err1"
					+ JSON.stringify(err1) + "err2:" + JSON.stringify(err2));
		}
	});
	return retData;
}

/**
 * 获取form表单数据，拼接成json格式
 * @param formName
 */
function getFormSimpleData(formName) {
	var initName=formName;
	var formName = formName!=""&formName!=null?"#"+formName+" ":"";
	if($(formName).length==0&&formName!="")
		formName="form[name='"+initName+"'] ";
	var first = $(formName + "input[name]");  
	var second=$(formName+"select[name]");
	var third=$(formName+"textarea[name]");  
	var arr=$.merge($.merge(first,second),third);
	var returnVal = {};
	$.each(arr,function(i,obj){	  
		
		if($(obj).attr("type")=="radio"){
			if(!this.checked)
				return;
		} 
		
		if($(obj).attr("name").indexOf(".")>0){
		   var names=$(obj).attr("name").split("."); 
		   var str="";
		   if($(obj).val()==""||$(obj).val()==null){
			   str="['"+names[0]+"']";
			   eval("returnVal"+str+"=null");
		   }else{
			   str="";
			   for(var i=0;i<names.length;i++){ 
				   str+="['"+names[i]+"']"
				   if(i<names.length-1)  {  
					  
				     if(eval("returnVal"+str)==null||eval("returnVal"+str)==undefined){
				    	 
					    eval("returnVal"+str+"={}");
				     }
				   }
				   else{
					   if($(obj).hasClass("dhxCalendar")&&$(obj).val()!=""&&$(obj).val()!=null)
						  eval("returnVal"+str+"=new Date(Date.parse($(obj).val().replace(/-/g,'/').replace(/\./g,'/')+' 12:00:00'))");
					   else{
						   
					      eval("returnVal"+str+"=$(obj).val()");
					   } 
				  } 
			   }
			}
		}
		else{    
			if($(obj).hasClass("dhxCalendar")&&$(obj).val()!=""&&$(obj).val()!=null) {
				var date=new Date(Date.parse($(obj).val().replace(/-/g,'/').replace(/\./g,'/')+" 12:00:00"));
				returnVal[($(obj).attr("name"))] = date;    
			} 
			else {
				returnVal[($(obj).attr("name"))] = $(obj).val();
			}
		}
	})  	  
	return returnVal;
}

/**
 * json对象回填到form表单
 * @param json
 * @param formName
 */

function initFormData(json,formName) {  
	if(json==null||json==""){ 
		json = {};	
	}
	if(typeof(json)=="string"){
		json = $.fromJSON(json);
	}     
	    var initName=formName;
		var formName = formName!=""&formName!=null?"#"+formName+" ":""; 
		if($(formName).length==0&&formName!="")
			formName="form[name='"+initName+"'] "; 
		var first = $(formName + "input[textboxname]");  
		var second=$(formName+"select[name]");
		var third=$(formName+"textarea[name]");   
		var four=$(formName+"label[name]");  
		var five=$(formName+"input[name]");  
		//var six=
		var arr=$.merge($.merge($.merge($.merge(first,second),third),four),five);
		$.each(arr,function(i,obj){
			var name=$(obj).attr("name");
			if(name=="")    
				return true;  
			var value;  
			try{			
			   if(name)
			     value=eval("json."+$(obj).attr("name"));   
			   else
				 value=eval("json."+$(obj).attr("textboxname"));  
			}
			catch(err){ 
				value=null;
			} 	        	       
			
			if(this.tagName.toUpperCase()=="INPUT"){
				
				if($(this).attr("type")&&($(this).attr("type")).toUpperCase()=="RADIO"){ 
					
					if($(this).val()==value){   
						this.checked = true; 
					}   
					else{
						this.checked = false; 
					} 
				}		
				else if($(this).attr("type")&&($(this).attr("type")).toUpperCase()=="CHECKBOX"){
					if(("," + value + ",").indexOf("," + $(this).val() + ",")!=-1){
						this.checked = true;
					}
				}  				
				else{   
					 if($(this).hasClass("easyui-textbox")){	 
						$(this).textbox('setValue',value);
					 }else if($(this).hasClass("easyui-combobox")){
						 $(this).combobox('setValue',value);
					 } 
		    		else 
					    $(this).val(value);					 
				} 
			}
			
			else if((this.tagName).toUpperCase()=="SELECT"){
			     if($(this).hasClass("easyui-combobox")){
					 $(this).combobox('setValue',value);
				 } else{
					 $(this).val(value); 
				 } 
			}
			
			else if((this.tagName).toUpperCase()=="TEXTAREA"){  
				$(this).val(value);
			}
			
			else if((this.tagName).toUpperCase()=="LABEL"){				
				if(value!=null){      
					if($(this).attr("type")=="date")
						$(this).text(formatDate(new Date(value),$(this).attr("format")));
					else 
						$(this).text(value);
				}
				
			}
		}); 
	return true;
}

function dateFormate(date) {
	var date1 = new Date(date);
	var year = date1.getFullYear();
	var month = date1.getMonth()+1;
	var day = date1.getDate();
	return year+"-"+month+"-"+day+" "+date1.toTimeString().substr(0, 5);
}
function simpleDateCallback(mm){
	if (!mm) return "";
	else return dateFormate(new Date(mm));
}
function HTMLDecode(text) { 
    var temp = document.createElement("div"); 
    temp.innerHTML = text; 
    var output = temp.innerText || temp.textContent; 
    temp = null; 
    return output; 
} 
String.prototype.trimEx=function(){return this.replace(/^[\s\n\r]*|[\s\n\r]*$/g,"")}
