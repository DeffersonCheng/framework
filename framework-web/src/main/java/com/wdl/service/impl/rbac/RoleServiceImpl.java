package com.wdl.service.impl.rbac;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wdl.base.service.impl.BaseServiceImpl;
import com.wdl.dao.rbac.RoleDao;
import com.wdl.entity.rbac.RoleEntity;
import com.wdl.service.rbac.RoleService;
/**
 * 角色业务实现
* @ClassName: RoleServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author sunfengle
* @date 2016年3月10日 下午1:31:12 
*
 */
@Service("RoleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Resource
	RoleDao roleDao;
	@Override
	public List<RoleEntity> findRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		List<RoleEntity> roles=roleDao.findRoleByUserId(userId);
		return roles;
	}

	@Override
	public boolean hasRole(Long userId, String roleCode) {
		List<RoleEntity> roleList = roleDao.findRoleByUserId(userId);
		for (RoleEntity roleEntity : roleList) {
			if(roleCode.equals(roleEntity.getCode())){
				return true;
			}
		}
		return false;
	}

	
}
