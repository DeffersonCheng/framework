package com.wdl.dao.rbac;

import java.util.List;

import com.wdl.base.dao.BaseDao;
import com.wdl.entity.rbac.UserRoleEntity;

public interface UserRoleDao extends BaseDao {
	/**
	 * 根据uuid,roleid查询实体并删除
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean deleteUserRole(Long userId,String[] roleId);

	/**
	 * 查询userRole根据userId
	 * @param userId
	 * @return
	 */
    public List<UserRoleEntity> findUserRoleByUserId(Long userId);
}	
