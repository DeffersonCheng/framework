package com.wdl.webapp.controller.rbac;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.wdl.base.pojo.AjaxResult;
import com.wdl.base.service.BaseService;
import com.wdl.util.EncoderHandler;
import com.wdl.entity.rbac.Org;
import com.wdl.entity.rbac.RoleEntity;
import com.wdl.entity.rbac.UserEntity;
import com.wdl.service.rbac.OrgService;
import com.wdl.service.rbac.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private BaseService baseService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource
	private OrgService orgService;

	/**
	 * 后台分页列表
	 * 
	 * @return
	 */
	@RequestMapping("/getUserList")
	public String getUserList() {

		return "/user/userList";
	}

	/**
	 * 后台返回添加或修改弹窗
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/userAddOrEdit")
	public String userAddOrEdit(Long id, HttpServletRequest request) {

		UserEntity userEntity = null;
		if (id != null) {
			userEntity = baseService.get(UserEntity.class, id);
		} else {
			userEntity = new UserEntity();
		}
		request.setAttribute("UserEntity", userEntity);
		return "user/userAddOrEdit";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ajaxGetParentUser")
	public void ajaxGetParentUser(Long id, HttpServletResponse response){
		String result = "";
		try{
			List<UserEntity> list = null;
			if(id != null){
				list = baseService.find("from UserEntity where id!=?", id);
			}else{
				list = baseService.find("from UserEntity");
			}
			if(list == null){
				list = new ArrayList<UserEntity>();
			}
			result = JSONArray.toJSONString(list);
		}catch(Exception e){
			logger.error("获取上级用户列表异常", e);
		}finally{
			try {
				OutputStream os = response.getOutputStream();
				os.write(result.getBytes("utf-8"));
				os.flush();
			} catch (Exception e) {
				logger.error("返回数据接收结果异常", e);
			}
		}
		
	}

	/**
	 * 新增、修改
	 * 
	 * @param userEntity
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/saveOrUpdate")
	@ResponseBody
	public AjaxResult saveOrUpdate(UserEntity userEntity) {

		AjaxResult result = new AjaxResult();
		result.setCode(Integer.parseInt(userService.saveOrUpdateUser(userEntity)));
		return result;
	}

	/**
	 * 用户权限
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/userRoleChackUrl")
	public String userRoleChack(String id, HttpServletRequest request) {

		request.setAttribute("userId", id);
		return "user/userRoleTree";
	}

	/**
	 * 用户权限
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/resetPwd")
	public String resetPwd(String id, HttpServletRequest request) {
		request.setAttribute("id", id);
		return "user/resetPwd";
	}

	/**
	 * 查询全部角色和用户角色
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/userRoleChacks")
	@ResponseBody
	public List<RoleEntity> userRoleChacks(Long userId, HttpServletRequest request) {

		List<RoleEntity> rlist = new ArrayList<RoleEntity>();
		rlist = userService.findUserRoleChacks(userId);
		return rlist;
	}

	/**
	 * 手机号格式验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();

	}

	/**
	 * 个人信息 userAddOrEdit
	 *
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/user_information")
	public String userInformation(Long id, HttpServletRequest request) {

		UserEntity userEntity = null;
		userEntity = baseService.get(UserEntity.class, id);
		Org org = orgService.get(Org.class, userEntity.getOrgId());
		request.setAttribute("UserEntity", userEntity);
		request.setAttribute("org", org == null ? new Org() : org);
		return "personal/userIndex";
	}

	/**
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping("/findLoginName")
	@ResponseBody
	public AjaxResult findLoginName(String loginName, HttpServletRequest request) {

		AjaxResult result = new AjaxResult();
		UserEntity userEntity = userService.findUserByloginName(loginName);
		if (userEntity != null) {
			result.setData(userEntity);
		} else {
			result.setData("");
		}
		return result;
	}

	@RequestMapping("/userRole/userRoleUpdate")
	public String userRoleUpdate() {
		return "forward:/userRole/userRoleUpdate";
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/relieve")
	@ResponseBody
	public AjaxResult relieve(Long id, HttpServletRequest request) {
		AjaxResult result = new AjaxResult();
		if (id != null) {
			UserEntity userEntity = userService.get(UserEntity.class, id);
			if (userEntity != null) {
				userEntity.setCountMistake(0);
				userService.update(userEntity);
				result.setCode(1);
			} else {
				result.setCode(2);
			}
		}
		return result;
	}

	@RequestMapping("/resetPassword")
	@ResponseBody
	public AjaxResult resetPassword(String loginPassword, Long userId, HttpServletRequest request,
			HttpSession session) {
		AjaxResult result = new AjaxResult();
		String loginName = (String) session.getAttribute("loginName");
		if (loginName != null && loginName.length() > 0 && loginPassword != null && loginPassword.length() > 0) {
			UserEntity userEntity = new UserEntity();
			userEntity = userService.findUserLoginAndPasword(loginName, EncoderHandler.encode("SHA1", loginPassword));
			if (userEntity == null) {
				userEntity = userService.findUserLoginAndPasword(loginName,
						EncoderHandler.encode("SHA1", loginName + loginPassword));
			}
			if (userEntity != null) {
				if (userId != null) {
					UserEntity resetUserEntity = userService.get(UserEntity.class, userId);
					if (resetUserEntity != null) {
						resetUserEntity.setPassWord(EncoderHandler.encode("SHA1", "123456"));
						userService.update(resetUserEntity);
						result.setCode(1);// 原密码错误
					}
				}
			} else {
				result.setCode(2);// 原密码错误
			}
		}
		return result;
	}
}
