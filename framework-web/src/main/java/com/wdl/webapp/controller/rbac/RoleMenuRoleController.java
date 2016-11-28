package com.wdl.webapp.controller.rbac;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdl.base.pojo.AjaxResult;
import com.wdl.entity.rbac.vo.MenuTreeVo;
import com.wdl.service.rbac.MenuService;
import com.wdl.service.rbac.RoleMenuService;
/**
 * 角色-菜单 控制类
* @ClassName: RoleMenuRoleController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author sunfengle
* @date 2016年3月10日 下午1:34:22 
*
 */
@Controller
@RequestMapping("/role")
public class RoleMenuRoleController {
	@Resource
	private RoleMenuService roleMenuService;
	private static final Logger logger=Logger.getLogger(RoleMenuRoleController.class);
	/**
	 * 增加，删除
	 * @param id
	 * @param trueCheck //增加
	 * @param falseChack//删除节点
	 * @return
	 */
	@Resource
	MenuService menuService;
	@RequestMapping(method = RequestMethod.POST, value ="/roleMenuUpdate")
	@ResponseBody
	public AjaxResult roleMenuUpdate(Long roleId,String[] trueCheck,String[] falseChack) {
		AjaxResult result=new AjaxResult();
		boolean tf = roleMenuService.saveOrDelete(roleId, trueCheck, falseChack);
		if(tf) result.setCode(1);
		Long userId=(Long) SecurityUtils.getSubject().getSession().getAttribute("id");
		//result.setData(userEntity);
		return result;
	}
	/**
	 * 角色授权
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/roleMenuChacks")
	@ResponseBody
	public List<MenuTreeVo> roleMenuChacks(Long roleId,HttpServletRequest request){
		
		return roleMenuService.findAll(roleId);
	}

}