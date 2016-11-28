package com.wdl.service.impl.rbac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wdl.base.service.impl.BaseServiceImpl;
import com.wdl.util.ResourcesUtil;
import com.wdl.dao.rbac.MenuDao;
import com.wdl.dao.rbac.RoleMenuDao;
import com.wdl.dao.rbac.UserRoleDao;
import com.wdl.entity.rbac.MenuEntity;
import com.wdl.entity.rbac.vo.TreeVo;
import com.wdl.service.rbac.MenuService;

@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	@Resource
	private MenuDao menuDao;

	@Resource
	private UserRoleDao userRoleDao;

	@Resource
	private RoleMenuDao roleMenuDao;

	public List<TreeVo> findAll() {

		List<MenuEntity> list = menuDao.list(MenuEntity.class);
		return generateTreeVo(list);
	}

	/**
	 * 根据用户ID查询menu集合
	 */
	@Override
	public List<TreeVo> findMenuByUserId(Long userId) {

		List<MenuEntity> menuList = new ArrayList<MenuEntity>();
		if (userId != null) {
			menuList = menuDao.findMenuByUserId(userId);
			if (menuList != null) {
				return generateTreeVo(removeUnnecessaryList(menuList));
			}
		}
		return null;
	}

	private String handleUrl(String str) {
		if (str.length() == 0)
			return str;
		str = str.trim();
		str = str.substring(0, str.lastIndexOf('/') + 1);
		return str + "*";
	}

	private List<TreeVo> generateTreeVo(List<MenuEntity> list) {

		List<TreeVo> vos = new ArrayList<TreeVo>();
		if (list != null && list.size() != 0) {
			for (MenuEntity menu : list) {
				if (menu != null && (menu.getCode() == null ? null : menu.getCode().length()) == 6) {
					TreeVo vo = new TreeVo();
					vo.setId(menu.getId().toString());
					vo.setCode(menu.getCode());
					vo.setUrl(menu.getUrl());
					vo.setPid(menu.getPid() == null ? null : menu.getPid());
					vo.setText(menu.getName());
					vo.setType(menu.getTypeCode());
					vo.setDeleted(menu.getIsDel());
					vo.setVersion(menu.getVersion());
					vo.setIcon(menu.getIcon());
					vo.setChildren(getChildren(menu.getId(), list));
					vos.add(vo);
				}
			}
		}
		return vos;
	}

	private List<TreeVo> getChildren(Long pid, List<MenuEntity> list) {

		List<TreeVo> vos = new ArrayList<TreeVo>();
		for (MenuEntity menu : list) {
			if (menu.getPid() != null && pid.equals(menu.getPid())) {
				TreeVo vo = new TreeVo();
				vo.setId(menu.getId().toString());
				vo.setCode(menu.getCode());
				vo.setUrl(menu.getUrl());
				vo.setPid(menu.getPid() == null ? null : menu.getPid());
				vo.setText(menu.getName());
				vo.setType(menu.getTypeCode());
				vo.setDeleted(menu.getIsDel());
				vo.setVersion(menu.getVersion());
				vo.setChildren(getChildren(menu.getId(), list));
				vos.add(vo);
			}
		}
		return vos;
	}

	@Override
	public void saveOrUpdate(MenuEntity menu) {

		if (menu.getPid() != null) {
			MenuEntity _menu = menuDao.get(MenuEntity.class, menu.getPid());
			menu.setPid(_menu.getId());
		}
		if (menu.getCode() == null || "".equals(menu.getCode())) {
			String maxCode = menuDao.getMaxCode(menu.getPid());
			menu.setCode(generateLevelCode(maxCode, 6));
		}

		menuDao.saveOrUpdate(menu);
	}

	public static String generateLevelCode(String levelCode, int stepSize) {

		int codeLength = levelCode.length();
		Integer num = Integer.parseInt(levelCode.substring(codeLength - stepSize, codeLength));
		num++;
		String code = num.toString();
		String zero = "";
		if (code.length() < stepSize) {
			for (int i = 0; i < stepSize - code.length(); i++) {
				zero += "0";
			}
		}
		return levelCode.substring(0, codeLength - stepSize) + zero + code;
	}

	// private List<TreeVo> generateTreeVo(List<MenuEntity> list) {
	// List<TreeVo> vos=new ArrayList<TreeVo>();
	// if(list!=null&&list.size()!=0){
	// for(MenuEntity menu:list){
	// if((menu.getCode()==null? null:menu.getCode().length())==6){
	// TreeVo vo=new TreeVo();
	// vo.setId(menu.getId().toString());
	// vo.setCode(menu.getCode());
	// vo.setUrl(menu.getUrl());
	// vo.setPid(menu.getId());
	// vo.setText(menu.getName());
	// vo.setSubs(getChildren(menu.getCode(),list));
	// vos.add(vo);
	// }
	// }
	// }
	// return vos;
	// }
	// private List<TreeVo> getChildren(String code, List<MenuEntity> list) {
	// List<TreeVo> vos=new ArrayList<TreeVo>();
	// for(MenuEntity menu:list){
	// if(menu.getCode().startsWith(code)&&!menu.getCode().equals(code)){
	// TreeVo vo=new TreeVo();
	// vo.setId(menu.getId().toString());
	// vo.setCode(menu.getCode());
	// vo.setUrl(menu.getUrl());
	// vo.setPid(menu.getId());
	// vo.setText(menu.getName());
	// vos.add(vo);
	// }
	// }
	// return vos;
	// }
	public MenuEntity saveEntity(MenuEntity menu) {

		return menu;
	}

	public List<MenuEntity> removeUnnecessaryList(List<MenuEntity> list) {

		List<MenuEntity> rsList = new ArrayList<MenuEntity>();
		for (MenuEntity menu : list) {
			if (!checkExist(rsList, menu)) {
				rsList.add(menu);
			}
		}
		return rsList;
	}

	private boolean checkExist(List<MenuEntity> list, MenuEntity menu) {

		boolean flag = false;
		for (MenuEntity m : list) {
			if (m.getId() == menu.getId()) {
				flag = true;
				break;
			}
		}
		return flag;
	}

}
