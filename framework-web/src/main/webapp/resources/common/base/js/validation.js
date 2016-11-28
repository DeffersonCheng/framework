    //扩展easyui表单的验证  
    $.extend($.fn.validatebox.defaults.rules, {  
        // 验证汉字
        CHS: {  
            validator: function (value) {  
                return /^[\u0391-\uFFE5]+$/.test(value);  
            },  
            message: '只能输入汉字.'  
        },  
        // 移动手机号码验证
        Mobile: {// value值为文本框中的值
            validator: function (value) {  
                var reg = /^(((13[0-9])|(14[0-9])|(15([0-9]))|(16([0-9]))|(17([0-9]))|(18[0-9]))\d{8})$/;  
                return reg.test(value);  
            },  
            message: '请输入正确的手机号码.'  
        },
      // 电话号码验证
        phone: {// value值为文本框中的值
            validator: function (value) { 
            	var phone=/^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}(-\d{1,4})?$/;
            	return phone.test(value); 
            	
            },  
            message: '请输入正确的电话号码.'  
        },  
        // 国内邮编验证
        ZipCode: {  
            validator: function (value) {  
                var reg = /^[0-9]\d{5}$/;  
                return reg.test(value);  
            },  
            message: '邮编必须为0~6位数.'  
        },
        numbers: {  
            validator: function (value) {  
                var reg = /^\d{6,18}$/;  
                return reg.test(value);  
            },  
            message: '层级编码只能为6~18位数字.'  
        },
        EnglishCheck: {
            validator: function (value) {  
                var reg = /^[a-zA-Z][a-zA-Z0-9_]{0,18}$/;  
                return reg.test(value);  
            },  
            message: '请以字母开头且只能为字母数字下划线.'  
        },URL: {
            validator: function (value) {  
            	var reg = /(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g
            	 return reg.test(value); 
            },  
            message: '编码只能输入字母数字或下划线长度最大为18位.'  
        },isNotBlank: {
            validator: function (value) {  
            	var reg = /^\s*$/
            	 if(!reg.test(value)){
            		 return true; 
            	 }
            },  
            message: '请输入有效字符'  
        },NumProduct: {
            validator: function (value) {  
            	var reg = /^\d{1,10}$/
            	return reg.test(value);
            },  
            message: '请输入数字,最大长度10位'  
        },Password: {
            validator: function (value) {  
            	var reg = /^(?![^a-z]+$)(?![^A-Z]+$)(?!\D+$)(?![^\`\~\!\@\#\$\%\^\&\*\(\)\_\-\=\+\\\|\]\}\[\{\'\"\;\:\/\?\.\>\,\<]+$)[A-Z][A-Za-z0-9\x21-\x7e]{8,19}$/
            	return reg.test(value);
            },  
            message: '9~20位，以大写字母开头，必须含有小写字母,数字和特殊字符.'  
        }
    }); 