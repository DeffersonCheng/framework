package com.wdl.service.impl.rbac;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wdl.base.service.impl.BaseServiceImpl;
import com.wdl.dao.rbac.UserRoleDao;
import com.wdl.entity.rbac.UserRoleEntity;
import com.wdl.service.rbac.UserRoleService;
@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl implements UserRoleService {
	@Resource
	private UserRoleDao userRoleDao;

	public boolean deleteUserRole(Long userId, String[] roleId) {
		return userRoleDao.deleteUserRole(userId, roleId);
	}

	@Override
	public List<UserRoleEntity> findUserRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		return userRoleDao.findUserRoleByUserId(userId);
	}
}