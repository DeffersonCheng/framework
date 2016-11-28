package com.wdl.entity.rbac;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wdl.base.entity.BaseEntity;

/**
 * 
 * 用户实体
 * 
 * @author bin
 *
 */
@Entity
@Table(name = "t_user")
public class UserEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6093546087036436583L;
	/**
	 * 用户名称
	 */
	@Column(name = "userName")
	private String userName;
	/**
	 * 登陆名
	 */
	@Column(name = "loginName")
	private String loginName;
	/**
	 * 登陆密码
	 */
	@Column(name = "passWord")
	private String passWord;
	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;
	/**
	 * 电话
	 */
	@Column(name = "phone")
	private String phone;
	/**
	 * 手机
	 */
	@Column(name = "mobile")
	private String mobile;

	@Column(name = "orgId")
	private Long orgId;

	@Column(name = "count_mistake")
	private Integer countMistake;

	/**
	 * 创建时间
	 */
	@Column(name = "count_mistake_time")
	private Date countMistakeTime;

	@Column(name = "validata_code")
	private String validataCode;

	@Column(name = "out_date")
	private Date outDate;

	/**
	 * 是否删除
	 */
	@Column(name = "isdel")
	private Integer isdel;
	
	@Column(name = "parentCode")
	private String parentCode;

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getCountMistake() {
		return countMistake;
	}

	public void setCountMistake(Integer countMistake) {
		this.countMistake = countMistake;
	}

	public Date getCountMistakeTime() {
		return countMistakeTime;
	}

	public void setCountMistakeTime(Date countMistakeTime) {
		this.countMistakeTime = countMistakeTime;
	}

	public String getValidataCode() {
		return validataCode;
	}

	public void setValidataCode(String validataCode) {
		this.validataCode = validataCode;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

}
