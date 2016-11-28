function Formula() {
}
/**
 * 求面积
 */
Formula.prototype.area = function(heigt, width) {
	return height * width;
}

/**
 * 求体积
 */
Formula.prototype.volume = function(width, height, length) {
	return width/100 * height/100 * length/100;
}

/**
 * 内容物重量
 */
Formula.prototype.weight=function(width,height,length){
	return this.accMul(this.volume(width, height, length), 0.3);
}
/**
 * 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。 调用：accAdd(arg1,arg2)
 * 返回值：arg1加上arg2的精确结果
 */
Formula.prototype.accAdd = function(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2))
	return (arg1 * m + arg2 * m) / m
}

/**
 * 说明：javascript的减法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。 调用：accSub(arg1,arg2)
 * 返回值：arg1减上arg2的精确结果
 */
Formula.prototype.accSub = function(arg1, arg2) {
	return this.accAdd(arg1, -arg2);
}

/**
 * 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。 调用：accMul(arg1,arg2)
 * 返回值：arg1乘以arg2的精确结果
 */
Formula.prototype.accMul = function(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
 
/**
 * javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
 * 
 */
Formula.prototype.accDiv = function(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""))
		r2 = Number(arg2.toString().replace(".", ""))
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

/**修复Firefox下保留小数位不准确的问题
 * 
 * @param num 数字
 * @param s 保留小数位
 * @returns 四舍五入后的结果
 */
Formula.prototype.toFixed = function(num, s) {
	var times = Math.pow(10, s)
	var des = num * times + 0.5
	des = parseInt(des, 10) / times
	return des;
}
/**
 * 字符串数组中是否含有某个字符串
 * @param strArr 字符串数组
 * @param str 字符串
 * @returns 
 */
Formula.prototype.contain = function(strArr, str) {
	for (var i = 0; i < strArr.length; i++) {
		if (strArr[i] == str)
			return true;
	}
	return false;
}
