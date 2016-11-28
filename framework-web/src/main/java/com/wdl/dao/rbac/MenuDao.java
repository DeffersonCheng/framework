package com.wdl.dao.rbac;

import java.util.List;

import com.wdl.base.dao.BaseDao;
import com.wdl.entity.rbac.MenuEntity;

public interface MenuDao extends BaseDao {
	/**
	 * 根据ID查询menu实体
	 * @param id
	 * @return
	 */
	public MenuEntity findMenuById(Long id);
	/**
	 * 根据用户ID查询菜单
	 * @param userId
	 * @return
	 */
	public List<MenuEntity> findMenuByUserId(Long userId);
    String getMaxCode(Long id);

}	
