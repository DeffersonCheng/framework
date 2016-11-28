package com.wdl.entity.rbac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wdl.base.entity.BaseEntity;

/**
 * 角色-菜单中间表
 * @author bin
 *
 */
@Entity
@Table(name = "t_role_menu")
public class RoleMenuEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单ID
	 */
	@Column(name="menuId")
	private Long menuId;
	/**
	 * 角色ID
	 */
	@Column(name="roleId")
	private Long roleId;
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	

}
