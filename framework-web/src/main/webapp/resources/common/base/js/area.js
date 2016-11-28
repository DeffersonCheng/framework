function viewAreaProvince(){
	ajaxPost(basePath+"/city/view_province",null,function(data){
		if(data.length>0){
			data[0].selected=true;
			$('#viewProvince').combobox({
				data:data,
				valueField:'id', 
				textField:'name' 
			});
			
		}
	});
}

function viewAreaProvinceGanged(obj){
	ajaxPost(basePath+"/city/view_province",null,function(data){
		if(data.length>0){
			data[0].selected=true;
			$('#viewProvince').combobox({
				onChange: function (n,o) {
					viewAreaCity(n,obj)
				},
				data:data,
				valueField:'districtCode', 
				textField:'districtName' ,
				onLoadSuccess : function(){
					if (!(obj == "0")) {
						$("#viewProvince").combobox('select',obj.substr(0,2)+"0000");
					}		
				}
				
			});
			
		}
	});
}

function viewAreaCity(code,obj){
	ajaxPost(basePath+"/city/view_city",{code:code},function(data){
		if(data.data.length>0){
			data.data[0].selected=true;
			var selectCode = "";
			$('#viewCity').combobox({
				onChange: function (n,o) {
					viewAreaDistrict(n,obj);
					selectCode = n;
				},
				data:data.data,
				valueField:'districtCode', 
				textField:'districtName' ,
				onLoadSuccess : function(){
					if (!(obj == "0")&&obj.substr(0,2)+"0000"==selectCode.substr(0,2)+"0000") {
						$("#viewCity").combobox('select',obj.substr(0,4)+"00");
					}		
				}
			});
		}
	});
}

function viewAreaDistrict(code,obj){
	ajaxPost(basePath+"/city/view_district",{code:code},function(data){
		if(data.data.length>0){
			data.data[0].selected=true;
			var selectCode = "";
			$('#viewDistrict').combobox({
				onChange: function (n,o) {
					selectCode = n;
				},
				data:data.data,
				valueField:'districtCode', 
				textField:'districtName' ,
				onLoadSuccess : function(){
					if (!(obj == "0")&&obj.substr(0,4)+"00"==selectCode.substr(0,4)+"00") {
						$("#viewDistrict").combobox('select',obj);
					}		
				}
			});
		}
	});
}

function allName(code){
	ajaxPost(basePath+"/city/all_name",{code:code},function(data){
		if(data.code.length>0){
			$("#cityAllName").html(data.code);
		}
	});
}


function cityName(code){
	var cityNames;
	ajaxPost(basePath+"/city/all_name",{code:code},function(data){
		if(data.code.length>0){
			//$("#cityAllName").html(data.code);
			cityNames= data.code;
		}
	});
	return cityNames;
}

/*if(obj!=0){
	var objProvince=obj.substring(0,2)+"0000";
	var objCity=obj.substring(0,3)+"000";
	$("#viewProvince").combobox('setValues',objProvince);
	$("#viewCity").combobox('setValues',objCity);
	$("#viewDistrict").combobox('setValues',obj);
}*/