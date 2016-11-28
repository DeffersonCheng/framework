package com.wdl.service.rbac;

import java.util.List;
import java.util.Set;

import com.wdl.base.service.BaseService;
import com.wdl.entity.rbac.MenuEntity;
import com.wdl.entity.rbac.vo.TreeVo;

public interface MenuService extends BaseService {

	public List<TreeVo> findAll() ;
	
	/**
	 * 根据用户ID查询
	 * @param userId
	 * @return
	 */
	public List<TreeVo> findMenuByUserId(Long userId);

    void saveOrUpdate(MenuEntity menu);
}
