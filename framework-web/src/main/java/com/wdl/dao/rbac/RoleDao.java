package com.wdl.dao.rbac;

import java.util.List;

import com.wdl.base.dao.BaseDao;
import com.wdl.entity.rbac.RoleEntity;
/**
 * 
* @ClassName: RoleDao 
* @Description: TODO 角色数据操作接口
* @author sunfengle
* @date 2016年3月10日 下午1:16:23 
*
 */
public interface RoleDao extends BaseDao {
	/**
	 * 查询启用权限
	 * @return
	 */
	public List<RoleEntity> findRoleIsDel();
	
	/**
	 * 根据用户ID获取所属角色
	 * @param userId
	 * @return
	 */
	public List<RoleEntity> findRoleByUserId(Long userId);
	
}	
