package com.wdl.dao.rbac;

import java.util.List;

import com.wdl.base.dao.BaseDao;
import com.wdl.entity.rbac.Org;

public interface OrgDao extends BaseDao {

	public List<Org> findByDeleted();

}
