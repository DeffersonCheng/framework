package com.wdl.service.rbac;

import java.util.List;

import com.wdl.base.service.BaseService;
import com.wdl.entity.rbac.RoleEntity;

/**
 * 角色业务逻辑
* @ClassName: RoleService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author sunfengle
* @date 2016年3月10日 下午1:29:36 
*
 */
public interface RoleService extends BaseService {

	public List<RoleEntity> findRoleByUserId(Long userId);
	public boolean hasRole(Long userId, String roleCode);
}
