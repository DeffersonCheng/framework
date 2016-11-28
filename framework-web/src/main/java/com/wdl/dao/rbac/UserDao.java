package com.wdl.dao.rbac;

import java.util.List;

import com.wdl.base.dao.BaseDao;
import com.wdl.entity.rbac.UserEntity;

public interface UserDao extends BaseDao {
	/**
	 * 根据用户名查询
	 * 
	 * @return
	 */
	public UserEntity findUserByloginName(String loginName);

	public UserEntity chackEmain(String validataCode, String id);

	public List<UserEntity> findByIsDel(Integer del);

	public List<UserEntity> findByRole(Long roleId);

	public UserEntity findUserLoginAndPasword(String LoginName, String password);

}
