package com.wdl.webapp.controller.rbac;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdl.base.pojo.AjaxResult;
import com.wdl.entity.rbac.RoleEntity;
import com.wdl.entity.rbac.UserRoleEntity;
import com.wdl.service.rbac.RoleService;
/**
 * 角色控制类
* @ClassName: RoleController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author sunfengle
* @date 2016年3月10日 下午1:33:08 
*
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	@Resource
	private RoleService roleService;

	
	@RequestMapping("/roleList")
	public String roleList() {
		return "role/roleList";
	}
	@RequestMapping("/roleAddOrEdit")
	public String roleAddOrEdit(Long id,HttpServletRequest request) {
		RoleEntity roleEntity=null;
		if(id!=null){
			roleEntity=roleService.get(RoleEntity.class, id);
		}else{
			roleEntity=new RoleEntity();
		}
		request.setAttribute("roleEntity",roleEntity);
		return "role/roleAddOrEdit";
	}
	@RequestMapping(method = RequestMethod.POST, value ="/roleRemove")
	@ResponseBody
	public AjaxResult roleRemove(RoleEntity roleEntity) {
		AjaxResult result=new AjaxResult();
		roleService.delete(roleEntity);
		result.setCode(1);
		return result;
	}
	@RequestMapping(method = RequestMethod.POST, value ="/saveOrUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdate(RoleEntity roleEntity) {
		AjaxResult result=new AjaxResult();
		if(roleEntity.getId()!=null&&!"".equals(roleEntity.getId())){
		    roleEntity.setUpdateDateTime(new Date());
		    roleEntity.setCreateDateTime(roleService.get(RoleEntity.class, roleEntity.getId()).getCreateDateTime());
		}
		roleService.saveOrUpdate(roleEntity);
		result.setCode(1);
		result.setData(roleEntity);
		return result;
	}
	/**
	 * 角色菜单
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/roleMenu")
	public String roleMenuChack(Long id,HttpServletRequest request){
		request.setAttribute("id",id);
		return "role/roleMenuTree";
	}
	
	@RequestMapping("/findRole")
    @ResponseBody
    public RoleEntity findUserRole(Long id){
	    RoleEntity roleEntity = roleService.get(RoleEntity.class, id);
        return roleEntity;
    }
}
