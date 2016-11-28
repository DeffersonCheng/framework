package com.wdl.dao.rbac.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.wdl.base.dao.impl.BaseDaoImpl;
import com.wdl.entity.rbac.MenuEntity;
import com.wdl.dao.rbac.MenuDao;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao {

	/**
	 * 根据ID查询menu实体
	 * 
	 * @param id
	 * @return
	 */
	public MenuEntity findMenuById(Long id) {

		if (id != null) {
			StringBuffer sBuffer = new StringBuffer();
			Map<String, Object> param = new HashMap<String, Object>();
			String hql = "from MenuEntity m where m.id=:id order by code asc";
			/*
			 * sBuffer.append(" from MenuEntity m where m.id=");
			 * sBuffer.append(id); sBuffer.append(" order by code asc");
			 * MenuEntity menuEntity = this.get(sBuffer.toString());
			 */
			param.put("id", id);
			MenuEntity menuEntity = this.get(hql, param);
			if (menuEntity != null) {
				return menuEntity;
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<MenuEntity> findMenuByUserId(Long userId) {

		Map<String, Object> param = new HashMap<String, Object>();
		if (userId != null) {
			String hql = "SELECT m FROM UserEntity u,UserRoleEntity ur,RoleMenuEntity rm, MenuEntity m  WHERE u.id=:userId and u.id=ur.userId and ur.roleId=rm.roleId and rm.menuId=m.id ORDER BY m.code";
			param.put("userId", userId);
			List<MenuEntity> list = this.find(hql, param);
			if (list != null) {
				return list;
			}
		}
		return null;
	}
	
    @Override
    public String getMaxCode(Long id) {

        String rs = null;
        String hql = "select max(code) from MenuEntity m where m.pid=:id and m.isDel='0'";
        Query query = super.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        rs = (String) query.uniqueResult();
        if (rs == null) {
            MenuEntity m = get(MenuEntity.class, id);
            rs = m.getCode() + "000000";
        }
        return rs;
    }
}
