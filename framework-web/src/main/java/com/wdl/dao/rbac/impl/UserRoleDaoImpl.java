package com.wdl.dao.rbac.impl;

//import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wdl.base.dao.impl.BaseDaoImpl;
import com.wdl.dao.rbac.UserRoleDao;
import com.wdl.entity.rbac.UserRoleEntity;
@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl implements UserRoleDao {
	/**
     * 根据uuid,roleid查询实体并删除
     */
	@Override
	public boolean deleteUserRole(Long userId, String[] roleId) {
		boolean flag=false;
		try {
			if (roleId.length>0) {
				for (int i = 0; i < roleId.length; i++) {
					UserRoleEntity userRoleEntity =  this.get("from UserRoleEntity ur where ur.userId="+userId+" and ur.roleId="+roleId[i]+"");
					this.delete(userRoleEntity);
				}
				flag=true;
			}
		} catch (Exception e) {
			System.out.println("错误"+e);
		}
		return flag;
	}

	/**
	 * 查询userRole根据userId
	 * @param userId
	 * @return
	 */
	@Override
	public List<UserRoleEntity> findUserRoleByUserId(Long userId) {
		List<UserRoleEntity> uList=new ArrayList<UserRoleEntity>();
		uList=this.find("from UserRoleEntity ur where ur.userId="+userId+"");
		if (uList!=null) {
			return uList;
		}
		return null;
	}

}