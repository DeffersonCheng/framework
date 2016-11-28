/**
 */
package com.wdl.dao.rbac.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wdl.base.dao.impl.BaseDaoImpl;
import com.wdl.dao.rbac.OrgDao;
import com.wdl.entity.rbac.Org;

@Repository("DepartmentDao")
public class OrgDaoImpl extends BaseDaoImpl implements OrgDao {

	@Override
	public List<Org> findByDeleted() {

		String hql = " from Org dt where dt.deleted=0  order by dt.levelCode asc";
		List<Org> list = this.find(hql);
		if (list != null) {
			return list;
		}
		return null;
	}
}
