package com.wdl.webapp.filter;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wdl.entity.rbac.UserEntity;
import com.wdl.service.rbac.MenuService;
import com.wdl.service.rbac.UserService;

/**
 * @author bin 系统安全认证实现类
 *
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {
	/**
	 * 认证回调函数, 登录时调用
	 */
	@Resource
	private UserService userService;
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 校验用户名密码
		UserEntity user = new UserEntity();
		user.setLoginName(token.getUsername());
		user.setPassWord(String.copyValueOf(token.getPassword()));
		List<UserEntity> userList = userService.findByExample(user);
		if (userList.size()>0) {
			UserEntity entity=userList.get(0);
			SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(entity, token.getPassword(), token.getUsername());
			// 注意此处的返回值没有使用加盐方式,如需要加盐，可以在密码参数上加
			
			return authenticationInfo;
		}
		return null;
	}
	@RequestMapping(value = "/logout")
    public String logout(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "已安全登出");
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.removeAttribute("permission");
		session.removeAttribute("id");
//		session.removeAttribute(SessionKeys.USERID);
//		session.removeAttribute(SessionKeys.USERNAME);
//		session.removeAttribute(SessionKeys.USERREALNAME);
		subject.logout();
		return "/admin/login";
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用 shiro 权限控制有三种 1、通过xml配置资源的权限
	 * 2、通过shiro标签控制权限 3、通过shiro注解控制权限
	 */
	@Resource
	MenuService menuService;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
}
