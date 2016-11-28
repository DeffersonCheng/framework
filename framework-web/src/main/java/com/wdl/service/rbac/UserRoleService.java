package com.wdl.service.rbac;

import java.util.List;

import com.wdl.base.service.BaseService;
import com.wdl.entity.rbac.UserRoleEntity;
/**
 * 
 * @author hutao
 * @date 2016年3月7日
 */
public interface UserRoleService extends BaseService {
	/**
	 * 根据uuid,roleid查询实体并删除
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean deleteUserRole(Long userId,String[] roleId);
	public List<UserRoleEntity> findUserRoleByUserId(Long userId);

}
