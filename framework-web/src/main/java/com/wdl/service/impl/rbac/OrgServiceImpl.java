package com.wdl.service.impl.rbac;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wdl.base.service.impl.BaseServiceImpl;
import com.wdl.dao.rbac.OrgDao;
import com.wdl.entity.rbac.Org;
import com.wdl.entity.rbac.vo.MenuTreeVo;
import com.wdl.service.rbac.OrgService;


@Service("orgService")
public class OrgServiceImpl extends BaseServiceImpl implements OrgService {

    @Resource
    private OrgDao orgDao;

    @Override
    public Org findById(Long id) {

    	Org org = this.get(Org.class, id);
        return org;
    }

    @Override
    public List<MenuTreeVo> findAll() {

        List<Org> list = this.list(Org.class);
        return findByParent(list);
    }

    @Override
    public List<Org> findByDeleted() {

        return orgDao.findByDeleted();
    }

    private List<MenuTreeVo> generateMenuTreeVo(List<Org> list) {

        List<MenuTreeVo> vos = new ArrayList<MenuTreeVo>();
        if (list != null && list.size() != 0) {
            for (Org menu : list) {
                if ((menu.getLevelCode() == null ? null : menu.getLevelCode().length()) == 6) {
                    MenuTreeVo vo = new MenuTreeVo();
                    vo.setId(menu.getId().toString());
                    vo.setCode(menu.getCode());
                    vo.setText(menu.getName());
                    vo.setLevelCode(menu.getLevelCode());
                    vo.setVersion(menu.getVersion());
                    vo.setChildren(getChildren(menu.getLevelCode(), list));
                    vos.add(vo);
                }
            }
        }
        return vos;
    }

    private List<MenuTreeVo> findByParent(List<Org> list) {

        List<MenuTreeVo> vos = generateMenuTreeVo(list);
        List<MenuTreeVo> treeVos = new ArrayList<MenuTreeVo>();
        MenuTreeVo vo = new MenuTreeVo();
        vo.setId("0");
        vo.setLevelCode("000000");
        vo.setText("组织机构");
        vo.setChildren(vos);
        treeVos.add(vo);
        return treeVos;
    }

    private List<MenuTreeVo> getChildren(String code, List<Org> list) {

        List<MenuTreeVo> vos = new ArrayList<MenuTreeVo>();
        for (Org menu : list) {
            if (menu.getLevelCode().startsWith(code) && !menu.getLevelCode().equals(code)) {
                MenuTreeVo vo = new MenuTreeVo();
                vo.setId(menu.getId().toString());
                vo.setCode(menu.getCode());
                vo.setLevelCode(menu.getLevelCode());
                vo.setText(menu.getName());
                vo.setVersion(menu.getVersion());
                vos.add(vo);
            }
        }
        return vos;
    }

}
