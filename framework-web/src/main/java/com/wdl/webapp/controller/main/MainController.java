package com.wdl.webapp.controller.main;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wdl.entity.rbac.vo.TreeVo;
import com.wdl.service.rbac.MenuService;

@Controller
public class MainController {
	@Resource(name = "menuService")
	private MenuService menuService;

	@RequestMapping("/")
	public String index(HttpServletRequest request) {
//		String userName = request.getParameter("userName");
//		String passWord = request.getParameter("passWord");
//		if (userName != null) {
//			Subject subject = SecurityUtils.getSubject();
//			UsernamePasswordToken token = new UsernamePasswordToken();
//			token.setUsername(userName);
//			token.setPassword(passWord.toCharArray());
//			subject.login(token);
//			return "redirect:/main";
//		} else {
//			return "login";
//		}
		return "redirect:/main";
	}

	@RequestMapping("/loginout")
	public String loginout(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		System.out.println(subject.getSession().getId());
		subject.logout();
		return "login";
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		return "login";
	}

	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		List<TreeVo> list = menuService.findAll();
		if (list != null && list.size() > 0) {
			request.setAttribute("list", list);
		}
		return "main";
	}
	@RequestMapping("/dashboard")
	public String dashboard(HttpServletRequest request) {
		return "dashboard";
	}
}
