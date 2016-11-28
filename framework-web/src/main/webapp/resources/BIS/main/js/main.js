$(function() {
    loadMenu();
}); 
function loadMenu(){
	$('#menuContainer').MenuToolBar({
		url : basePath + "/menu/getMenuList"
	});
	var count = '${count?default(0)}';
	if (count > 0) {
		$("#messageReadIco").css("color", "red")
		$("#messageReadText").css("color", "red")
	}
}
