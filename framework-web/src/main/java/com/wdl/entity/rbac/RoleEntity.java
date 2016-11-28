package com.wdl.entity.rbac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wdl.base.entity.BaseEntity;

/**
 * 角色实体
 * @author bin
 *
 */
@Entity
@Table(name = "t_role")
public class RoleEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色名称
	 */
	@Column(name="name")
	private String name;
	/**
	 * 角色编码
	 */
	@Column(name="code")
	private String code;
	/**
	 * 是否删除
	 */
	@Column(name="isDel")
	private Integer isDel;
	
	 @Transient
	 private int isChack;
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public int getIsChack() {
		return isChack;
	}
	public void setIsChack(int isChack) {
		this.isChack = isChack;
	}
	
}