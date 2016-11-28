package com.wdl.dao.rbac.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wdl.base.dao.impl.BaseDaoImpl;
import com.wdl.dao.rbac.RoleDao;
import com.wdl.entity.rbac.RoleEntity;
/**
 * 
* @ClassName: RoleDao 
* @Description: TODO 角色数据操作实现
* @author sunfengle
* @date 2016年3月10日 下午1:16:23 
*
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	/**
	 * 查询启用权限
	 */
	public List<RoleEntity> findRoleIsDel() {
		List<RoleEntity> rlist=new ArrayList<RoleEntity>();
		rlist=this.find("from RoleEntity re where re.isDel=0");
		if (rlist!=null) {
			return rlist;
		}
		return null;
	}

	@Override
	public List<RoleEntity> findRoleByUserId(Long userId) {
		String hql = "select re from RoleEntity re, UserRoleEntity ure where re.id = ure.roleId and ure.userId = :userId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return this.find(hql, params);
	}
}
