package com.wdl.base.pojo;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * @author bin
 *
 */
public class AjaxResult implements Serializable{
	private static final long serialVersionUID = 1770337433545741411L;
	/**
	 * 状态码
	 */
	private int code;
	private String message;
	/**
	 * 结果集
	 */
	private Object data;

	
	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}


	@Override
	public String toString() {
	    return new Gson().toJson(this);
	}

}
