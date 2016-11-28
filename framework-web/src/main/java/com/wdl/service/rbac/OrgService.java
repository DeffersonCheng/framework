/**
 */
package com.wdl.service.rbac;

import java.util.List;

import com.wdl.base.service.BaseService;
import com.wdl.entity.rbac.Org;
import com.wdl.entity.rbac.vo.MenuTreeVo;


/**
 * DepartmentService
 * 
 * @author jinx 2016年3月24日 下午10:02:14
 */
public interface OrgService extends BaseService {

    public Org findById(Long id);

    public List<MenuTreeVo> findAll();

    public List<Org> findByDeleted();

}
