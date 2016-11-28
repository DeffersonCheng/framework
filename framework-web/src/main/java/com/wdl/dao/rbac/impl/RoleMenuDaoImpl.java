package com.wdl.dao.rbac.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wdl.base.dao.impl.BaseDaoImpl;
import com.wdl.dao.rbac.RoleMenuDao;
import com.wdl.entity.rbac.RoleMenuEntity;

/**
 * 
 * @ClassName: RoleDao
 * @Description: TODO 角色-菜单 数据操作实现
 * @author sunfengle
 * @date 2016年3月10日 下午1:16:23
 *
 */
@Repository("roleMenuDao")
public class RoleMenuDaoImpl extends BaseDaoImpl implements RoleMenuDao {

	public List<RoleMenuEntity> findByRoleId(Long roleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		String hql = "from RoleMenuEntity where roleId =:roleId";
		param.put("roleId", roleId);
		List<RoleMenuEntity> roleMenuList = find(hql, param);
		// List<RoleMenuEntity> roleMenuList = find("from RoleMenuEntity where
		// roleId = " + roleId);
		return roleMenuList;
	}

	public List<RoleMenuEntity> findByRoleIdAndMenuId(Long roleId, String menuId) {
		Map<String, Object> param = new HashMap<String, Object>();
		String hql = "from RoleMenuEntity ur where ur.roleId=:roleId and ur.menuId=:menuId";
		param.put("roleId", Long.valueOf(roleId));
		param.put("menuId", Long.valueOf(menuId));
		List<RoleMenuEntity> roleMenuEntity = this.find(hql, param);
		return roleMenuEntity;
	}
}
