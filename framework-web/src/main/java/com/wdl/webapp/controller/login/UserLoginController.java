package com.wdl.webapp.controller.login;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdl.base.pojo.AjaxResult;
import com.wdl.entity.rbac.UserEntity;
import com.wdl.service.rbac.MenuService;
import com.wdl.service.rbac.RoleService;
import com.wdl.service.rbac.UserService;
import com.wdl.util.EncoderHandler;
import com.wdl.webapp.filter.SystemAuthorizingRealm;

/**
 * @ClassName: userLoginController
 * @Description: TODO
 * @author sunfengle
 * @date 2016年3月22日
 */
@Controller
@RequestMapping("/userLogin")
public class UserLoginController{
	private final static Logger logger=Logger.getLogger(UserLoginController.class);
	@Resource
	private UserService userService;

	@Resource
	RoleService roleService;
	@Resource
	private SystemAuthorizingRealm systemAuthorizingRealm;

	@RequestMapping("/userLogin")
	public String userLogin() {
		return "login/userLogin";
	}

	@RequestMapping("/change_password")
	public String userPassword() {
		return "login/change_password";
	}

	@RequestMapping(method=RequestMethod.POST,value="/selectUser")
	@ResponseBody
	public AjaxResult selectUser(String username, String password, String vcode, ServletRequest request,
			ServletResponse response) {
		logger.info("用户登录 用户:"+username);
		AjaxResult result = new AjaxResult();
		try {
			UserEntity entity = userService.findUserByloginName(username);
			if (entity != null) {
				result.setCode(UsetLogin(username, password, vcode, request, response));
			}
		} catch (AuthenticationException ex) {
			logger.error("",ex);
		}
		return result;
	}
	@Resource
	MenuService menuService;
	private int UsetLogin(String username, String password, String vcode, ServletRequest request,
			ServletResponse response) {
		int result = 0;
		UserEntity user = new UserEntity();
		user.setLoginName(username);
		user.setPassWord(EncoderHandler.encode("SHA1", password));
		user.setIsdel(0);
		List<UserEntity> userList = userService.findByExample(user);
		if (userList.size() > 0) {
			user = userList.get(0);
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			session.setAttribute("loginName", user.getLoginName());
			session.setAttribute("userName", user.getUserName());
			session.setAttribute("id", user.getId());
			session.setAttribute("userId", user.getId());
			subject.login(new UsernamePasswordToken(username, EncoderHandler.encode("SHA1", password)));
			/*
			 * try { onAccessDenied(request, response); } catch (Exception e) {
			 * // TODO Auto-generated catch block e.printStackTrace(); }
			 */
			if (subject.isAuthenticated()) {
				result = 1;
			}
		} else {
			result = 3;
		}
		return result;
	}

	@RequestMapping("/userLogout")
	public String userLogout() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (session != null) {
			session.removeAttribute("id");
			session.removeAttribute("loginName");
			session.removeAttribute("userName");
			session.removeAttribute("userId");
		}
		subject.logout();
		return "login/userLogin";
	}

	@RequestMapping(value ="/update_password")
	@ResponseBody
	public AjaxResult updatePassword(String password, String verifyCode) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		Long userId = Long.valueOf(session.getAttribute("updatePasswordUserId").toString());
		AjaxResult result = new AjaxResult();
		UserEntity userEntity = userService.get(UserEntity.class, userId);

		if (userEntity != null) {
			if (verifyCode != null) {
				if (verifyCode.equals(userEntity.getValidataCode())) {
					if (password != null && password.length() > 0) {
						if (!userEntity.getPassWord().equals(EncoderHandler.encode("SHA1", password))) {
							long outTime = new Date().getTime() - userEntity.getOutDate().getTime();
							if (outTime <= 30 * 60 * 1000) {
								userEntity.setPassWord(EncoderHandler.encode("SHA1", password));
								userService.update(userEntity);
								subject.logout();
								result.setCode(3);
							} else {
								result.setCode(2); // 超时
							}
						} else {
							result.setCode(5);// 相同的密码
						}
					} else {
						result.setCode(4);// 没有输入密码
					}
				} else {
					result.setCode(7);// 验证码错误
				}

			} else {
				result.setCode(6);// 验证码不能为空
			}
		} else {
			result.setCode(1);// key 错误
		}

		return result;
	}

	@RequestMapping(value ="/into_update_password")
	public String update_password(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (session != null) {
			return "user/update_password";
		} else {
			return "login/userLogin";
		}
	}

	@RequestMapping(value ="/user_update_password")
	@ResponseBody
	public AjaxResult userUpdatePassword(String password, String oldPassword) {

		AjaxResult result = new AjaxResult();
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (session != null) {
			UserEntity userEntity = userService.get(UserEntity.class,
					Long.valueOf(session.getAttribute("id").toString()));
			if (userEntity != null) {
				if (password != null && password.length() > 0) {
					if (userEntity.getPassWord().equals(EncoderHandler.encode("SHA1", oldPassword))) {
						if (!userEntity.getPassWord().equals(EncoderHandler.encode("SHA1", password))) {
							userEntity.setPassWord(EncoderHandler.encode("SHA1", password));
							userService.update(userEntity);
							result.setCode(3);
						} else {
							result.setCode(5);// 相同的密码
						}
					} else {
						result.setCode(6);// 原密码错误
					}
				} else {
					result.setCode(4);// 没有输入密码
				}
			} else {
				result.setCode(1);// 没找到
			}
		}
		return result;
	}
}