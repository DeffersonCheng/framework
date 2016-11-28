package com.wdl.webapp.controller.rbac;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdl.base.pojo.AjaxResult;
import com.wdl.base.service.BaseService;
import com.wdl.entity.rbac.UserRoleEntity;
import com.wdl.service.rbac.UserRoleService;
/**
 * 用户权限中间表操作（后台）
 * @author hutao
 * @date 2016年3月8日
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController {
	
	@Resource
	private BaseService baseService;
	@Resource
	private UserRoleService userRoleService;

	/**
	 * 增加，删除
	 * @param id
	 * @param trueCheck //增加
	 * @param falseChack//删除节点
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value ="/userRoleUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdate(Long userId,String[] trueCheck,String[] falseChack) {
		AjaxResult result=new AjaxResult();
		try {
			if (userId!=null) {
				//增加用户选中节点
				UserRoleEntity userRoleEntity=new UserRoleEntity();
				for (int i = 0; i < trueCheck.length; i++) {
					userRoleEntity.setUserId(userId);
					userRoleEntity.setRoleId(Long.valueOf(trueCheck[i]));
					baseService.save(userRoleEntity);
				}
				userRoleService.deleteUserRole(userId, falseChack);
			}
		} catch (Exception e) {
		}
		result.setCode(1);
		return result;
	}
}