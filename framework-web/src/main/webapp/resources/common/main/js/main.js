$(document).ready(function() {
	loadMenuTree();
	$('.tree').tree({
		onClick : function(node) {
			if (!node.url) {
				return;
			}
			loadPage(basePath+node.url);
		}
	});
//	window.location.replace(basePath+"/main"); 
	//$("#mainDiv").load(basePath+"/menu/menuList");
});
function loadMenuTree() {
	ajaxPost(basePath + "/menu/getMenuList", null, function(data) {
		if (data) {
			$.each(data, function(i, n) {
				var selected=(i==0?true:false);
				$('#nav').accordion(
					'add',
					{
						menuid : n.id,
						title : n.text,
						selected : selected,
						content : '<div style="padding:10px"><ul id="menu_'+n.id+'">' + '</ul></div>',
					});
				$('#menu_'+n.id).tree({
					data: n.children,
					parentField:"pid",
					textFiled:"text",
					idFiled:"id"
				});
			});
		}
	});
}
function loadPage(url){
	url+=(url.indexOf("?")==-1?"?"+(new Date().getTime()):"&"+(new Date().getTime()));
	$.get(url, function(response,status,xhr){
		if(status=="success"){
			$("body>*").each(function(){
				var $target=$(this);
				if (!$target.hasClass("main-container")){
					$target.remove();
				}
			});
			$("#mainDiv").html(response);
		}else{
			alert("出错了！！！");
		}
	  });
//	$("#mainDiv").load(url,function(response,status,xhr){
//		if(status=="success"){
//			if(response){
//				try{
//					var result = jQuery.parseJSON(response);
//					if(result.code==100){ 
//						$("#mainDiv").html("");
//						alert(result.data);
//					}
//				}catch(e){
//					return response;
//				}
//			}
//		}
//	});
}


function format(time, format){
    var t = new Date(time);
    var tf = function(i){return (i < 10 ? '0' : '') + i};
    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
        switch(a){
            case 'yyyy':
                return tf(t.getFullYear());
                break;
            case 'MM':
                return tf(t.getMonth() + 1);
                break;
            case 'mm':
                return tf(t.getMinutes());
                break;
            case 'dd':
                return tf(t.getDate());
                break;
            case 'HH':
                return tf(t.getHours());
                break;
            case 'ss':
                return tf(t.getSeconds());
                break;
        }
    })
}
