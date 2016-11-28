package com.wdl.dao.rbac;

import java.util.List;

import com.wdl.base.dao.BaseDao;
import com.wdl.entity.rbac.RoleMenuEntity;

/**
 * 
 * @ClassName: RoleDao
 * @Description: TODO 角色-菜单数据操作 接口
 * @author sunfengle
 * @date 2016年3月10日 下午1:16:23
 *
 */
public interface RoleMenuDao extends BaseDao {
	/**
	 * 根据角色Id查询所有权限菜单
	 * 
	 * @param roleId
	 * @return List<RoleMenuEntity>
	 */
	List<RoleMenuEntity> findByRoleId(Long roleId);

	/**
	 * 根据角色ID 和 菜单 ID 查询角色菜单实体
	 * 
	 * @param roleId
	 * @param falseChack
	 * @return RoleMenuEntity
	 */
	List<RoleMenuEntity> findByRoleIdAndMenuId(Long roleId, String menuId);
}
