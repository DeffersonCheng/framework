package com.wdl.service.rbac;

import java.util.List;
import java.util.Set;

import com.wdl.base.service.BaseService;
import com.wdl.entity.rbac.RoleEntity;
import com.wdl.entity.rbac.UserEntity;

/**
 * 
 * @author hutao
 * @date 2016年3月7日
 */
public interface UserService extends BaseService {
	/**
	 * 查询用户全部角色中的用户权限（带全部角色）
	 * 
	 * @param userId
	 * @return
	 */
	public List<RoleEntity> findUserRoleChacks(Long userId);

	public String saveOrUpdateUser(UserEntity userEntity);

	public UserEntity findUserByloginName(String loginName);

	public UserEntity chackEmain(String validataCode, String id);

	public List<UserEntity> findByIsDel(Integer del);

	public Set<String> getRoleNameSetByUserId(Long userId);

	public UserEntity findUserLoginAndPasword(String LoginName, String password);

}
