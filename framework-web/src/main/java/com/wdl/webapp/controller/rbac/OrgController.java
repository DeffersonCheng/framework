package com.wdl.webapp.controller.rbac;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdl.base.pojo.AjaxResult;
import com.wdl.entity.rbac.Org;
import com.wdl.entity.rbac.vo.MenuTreeVo;
import com.wdl.service.rbac.OrgService;

@Controller
@RequestMapping("/org")
public class OrgController {

	@Resource
	private OrgService orgService;

	@RequestMapping("/tree")
	@ResponseBody
	public List<MenuTreeVo> tree() {

		return orgService.findAll();
	}

	@RequestMapping("/get")
	@ResponseBody
	public Org get(String id) {

		Org org = orgService.get(Org.class, id);
		return org;
	}

	@RequestMapping("/index")
	public String index() {

		return "org/index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/edit/{id}")
	public String edit(@PathVariable("id") Long id, HttpServletRequest request) {

		Org org = null;
		if (!id.equals(Long.valueOf("0"))) {
			org = orgService.get(Org.class, id);
		} else {
			org = new Org();
		}
		request.setAttribute("org", org);
		return "org/edit";
	}

	@RequestMapping("/view_by_deleted")
	@ResponseBody
	public List<Org> delete() {

		List<Org> list = new ArrayList<Org>();
		list = orgService.findByDeleted();
		return list;
	}

	@RequestMapping(method = RequestMethod.POST, value ="/delete")
	@ResponseBody
	public AjaxResult delete(Org org) {

		AjaxResult result = new AjaxResult();
		// orgService.delete(department);

		org = orgService.get(Org.class, org.getId());
		orgService.delete(org);

		result.setCode(1);
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, value ="/save_or_update")
	@ResponseBody
	public AjaxResult saveOrUpdate(Org org, HttpServletRequest request) {

		AjaxResult result = new AjaxResult();
		if (org != null) {
			if (org.getId() != null && !"".equals(org.getId())) {
				Org orgOld = new Org();
				orgOld = orgService.get(Org.class, org.getId());
				if (null != orgOld.getParentId()) {
					org.setParentId(orgOld.getParentId());
				}
			} else {
				org.setParentId(org.getParentId());
			}
			orgService.saveOrUpdate(org);
			result.setCode(1);
			result.setData(org);
		}
		return result;
	}
}
