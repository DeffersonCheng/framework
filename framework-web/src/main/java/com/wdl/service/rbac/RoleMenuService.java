package com.wdl.service.rbac;

import java.util.List;

import com.wdl.base.service.BaseService;
import com.wdl.entity.rbac.vo.MenuTreeVo;

/**
 * 角色-菜单  业务接口
* @ClassName: RoleMenuService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author sunfengle
* @date 2016年3月10日 下午1:29:09 
*
 */
public interface RoleMenuService extends BaseService {
	/**
	 * 根据角色Id查询所有权限菜单
	 * @param roleId
	 * @return List<RoleMenuEntity>
	 */
	List<MenuTreeVo> findAll(Long roleId) ;
	/**
	 * 根据角色ID 菜单ID 删除或增加
	 * @param roleId
	 * @param trueCheck
	 * @param falseChack
	 * @return bolean
	 */
	boolean saveOrDelete( Long roleId,String[] trueCheck,String[] falseChack);
	/**
	 * 查询所有菜单
	 * @return List<MenuTreeVo>
	 */
	public List<MenuTreeVo> findAll();
}
