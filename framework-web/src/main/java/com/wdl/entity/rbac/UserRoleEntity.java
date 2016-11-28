package com.wdl.entity.rbac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wdl.base.entity.BaseEntity;

/**
 * 用户-角色中间表
 * @author bin
 *
 */
@Entity
@Table(name = "t_user_role")
public class UserRoleEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@Column(name="userId")
	private Long userId;
	/**
	 * 角色ID
	 */
	@Column(name="roleId")
	private Long roleId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}
