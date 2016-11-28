package com.wdl.service.impl.rbac;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wdl.base.service.impl.BaseServiceImpl;
import com.wdl.dao.rbac.MenuDao;
import com.wdl.dao.rbac.RoleMenuDao;
import com.wdl.entity.rbac.MenuEntity;
import com.wdl.entity.rbac.RoleMenuEntity;
import com.wdl.entity.rbac.vo.MenuTreeVo;
import com.wdl.service.rbac.RoleMenuService;

/**
 * 角色-菜单 业务实现
 * 
 * @ClassName: RoleMenuServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author sunfengle
 * @date 2016年3月10日 下午1:30:42
 *
 */
@Service("RoleMenuService")
public class RoleMenuServiceImpl extends BaseServiceImpl implements RoleMenuService {
	@Resource
	private MenuDao menuDao;
	@Resource
	private RoleMenuDao roleMenuDao;

	public List<MenuTreeVo> findAll(Long roleId) {
		// TODO Auto-generated method stub
		List<MenuEntity> list = menuDao.list(MenuEntity.class);
		List<RoleMenuEntity> roleMenuList = roleMenuDao.findByRoleId(roleId);
		for (RoleMenuEntity roleMenuEntity1 : roleMenuList) {
			for (MenuEntity menuEntity : list) {
				if (roleMenuEntity1.getMenuId().equals(menuEntity.getId())) {
					menuEntity.setChack(true); // 选中
				} else if (menuEntity.isChack() != true) {
					menuEntity.setChack(false); // 未选中
				}
			}
		}
		return generateMenuTreeVo(list);
	}

	public List<MenuTreeVo> findAll() {
		// TODO Auto-generated method stub
		List<MenuEntity> list = menuDao.list(MenuEntity.class);
		return PParentNode(list);
	}

	private List<MenuTreeVo> generateMenuTreeVo(List<MenuEntity> list) {

		List<MenuTreeVo> vos = new ArrayList<MenuTreeVo>();
		if (list != null && list.size() != 0) {
			for (MenuEntity menu : list) {
				if ((menu.getCode() == null ? null : menu.getCode().length()) == 6) {
					MenuTreeVo vo = new MenuTreeVo();
					vo.setId(menu.getId().toString());
					vo.setCode(menu.getCode());
					vo.setUrl(menu.getUrl());
					vo.setPid(menu.getPid() == null ? null : menu.getPid());
					vo.setText(menu.getName());
					vo.setVersion(menu.getVersion());
					vo.setChecked(menu.isChack());
					vo.setDataChecked(menu.isChack());
					vo.setChildren(getChildren(menu.getId(), list));
					vos.add(vo);
				}
			}
		}
		return vos;
	}

	private List<MenuTreeVo> PParentNode(List<MenuEntity> list) {
		List<MenuTreeVo> vos = generateMenuTreeVo(list);
		List<MenuTreeVo> treeVos = new ArrayList<MenuTreeVo>();
		MenuTreeVo vo = new MenuTreeVo();
		vo.setId("0");
		vo.setCode("000000");
		vo.setText("菜单管理");
		vo.setChildren(vos);
		treeVos.add(vo);
		return treeVos;
	}

	private List<MenuTreeVo> getChildren(Long code, List<MenuEntity> list) {
		List<MenuTreeVo> vos = new ArrayList<MenuTreeVo>();
		for (MenuEntity menu : list) {
			if (menu.getPid() != null && code.equals(menu.getPid())) {
				MenuTreeVo vo = new MenuTreeVo();
				vo.setId(menu.getId().toString());
				vo.setCode(menu.getCode());
				vo.setUrl(menu.getUrl());
				vo.setPid(menu.getPid() == null ? null : menu.getPid());
				vo.setText(menu.getName());
				vo.setVersion(menu.getVersion());
				vo.setChecked(menu.isChack());
				vo.setDataChecked(menu.isChack());
				vo.setChildren(getChildren(menu.getId(), list));
				vos.add(vo);
			}
		}
		return vos;
	}

	public boolean saveOrDelete(Long roleId, String[] trueCheck, String[] falseChack) {
		// TODO Auto-generated method stub
		boolean tf = false;
		try {
			if (roleId != null) {
				// 增加用户选中节点
				RoleMenuEntity roleMenuEntity;
				for (int i = 0; i < trueCheck.length; i++) {
					roleMenuEntity = new RoleMenuEntity();
					roleMenuEntity.setRoleId(roleId);
					roleMenuEntity.setMenuId(Long.valueOf(trueCheck[i]));
					roleMenuDao.save(roleMenuEntity);
				}
				// 删除用户节点
				for (int j = 0; j < falseChack.length; j++) {
					List<RoleMenuEntity> roleMenuEntities = roleMenuDao.findByRoleIdAndMenuId(roleId, falseChack[j]);
					if (roleMenuEntities != null) {
						roleMenuDao.batchDelete(roleMenuEntities);
					}
				}
			}
			tf = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return tf;
	}

}
