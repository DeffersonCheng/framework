package com.wdl.dao.rbac.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wdl.base.dao.impl.BaseDaoImpl;
import com.wdl.util.EncoderHandler;
import com.wdl.dao.rbac.UserDao;
import com.wdl.entity.rbac.UserEntity;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	/**
	 * 根据用户名查询
	 */
	@Override
	public UserEntity findUserByloginName(String loginName) {
		if (loginName != null) {
			Map<String, Object> param = new HashMap<String, Object>();
			String hql = "from UserEntity ue where ue.loginName=:loginName";
			param.put("loginName", loginName);
			UserEntity userEntity = new UserEntity();
			userEntity = this.get(hql, param);
			if (userEntity != null) {
				return userEntity;
			}
			/*
			 * String hql="from UserEntity ue where ue.loginName='"
			 * +loginName+"'"; UserEntity userEntity=new UserEntity();
			 * userEntity=this.get(hql); if (userEntity!=null) { return
			 * userEntity; }
			 */
		}
		return null;
	}

	@Override
	public UserEntity chackEmain(String validataCode, String id) {

		if (validataCode != null && id != null) {
			Map<String, Object> param = new HashMap<String, Object>();
			String hql = "from UserEntity u where u.validataCode=:validataCode";
			param.put("validataCode", validataCode);
			UserEntity userEntity = this.get(hql, param);
			if (userEntity != null) {
				if (EncoderHandler.encode("SHA1", userEntity.getId().toString()).equals(id)) {
					return userEntity;
				}
			}
		}
		return null;
	}

	@Override
	public List<UserEntity> findByIsDel(Integer del) {
		if (del != null) {
			Map<String, Object> param = new HashMap<String, Object>();
			String hql = " from UserEntity u where u.isdel=:isdel";
			param.put("isdel", del);
			List<UserEntity> list = this.find(hql, param);
			if (list != null) {
				return list;
			}
		}
		return null;
	}

	@Override
	public List<UserEntity> findByRole(Long roleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		String hql = " from UserEntity u where u.id in( select userId from UserRoleEntity ur where ur.roleId = :roleId)";
		param.put("roleId", roleId);
		List<UserEntity> list = this.find(hql, param);
		if (list != null) {
			return list;
		}
		return null;
	}

	@Override
	public UserEntity findUserLoginAndPasword(String LoginName, String password) {

		if (LoginName != null && LoginName.length() > 0 && password != null && password.length() > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			String hql = " from UserEntity u where u.loginName=:loginName and u.passWord=:passWord";
			params.put("loginName", LoginName);
			params.put("passWord", password);
			UserEntity userEntity = this.get(hql, params);
			if (userEntity != null) {
				return userEntity;
			}
		}
		return null;
	}
}
