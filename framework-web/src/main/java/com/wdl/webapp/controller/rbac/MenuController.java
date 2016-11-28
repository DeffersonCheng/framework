package com.wdl.webapp.controller.rbac;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wdl.base.pojo.AjaxResult;
import com.wdl.util.SessionUtil;
import com.wdl.entity.rbac.MenuEntity;
import com.wdl.entity.rbac.vo.TreeVo;
import com.wdl.service.rbac.MenuService;

/**
 * 菜单
 * 
 * @author bin
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @RequestMapping("/getMenuList")
    @ResponseBody
    public List<TreeVo> getMenuList() {

        List<TreeVo> list = menuService.findMenuByUserId(Long.valueOf(SessionUtil.getSession().getAttribute("id") + ""));
        // List<TreeVo> list = menuService.findAll();
        return list;
    }

    @RequestMapping("/menuList")
    public String menuList() {
        return "menu/menuList";
    }

    @RequestMapping(method = RequestMethod.POST, value ="/menuRemove")
    @ResponseBody
    public AjaxResult menuRemove(MenuEntity demoEntity) {

        AjaxResult result = new AjaxResult();
        menuService.delete(demoEntity);
        result.setCode(1);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value ="/saveOrUpdate")
    @ResponseBody
    public AjaxResult saveOrUpdate(MenuEntity demoEntity) {

        AjaxResult result = new AjaxResult();
        menuService.saveOrUpdate(demoEntity);
        result.setCode(1);
        result.setData(demoEntity);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    @ResponseBody
    public AjaxResult edit(MenuEntity menu) {

        AjaxResult result = new AjaxResult();
        menu.setIsDel("0");
        menuService.saveOrUpdate(menu);
        result.setCode(1);
        result.setData(menu);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/get_all_menu")
    @ResponseBody
    public List<TreeVo> getAllMenu() {

        List<TreeVo> list = menuService.findAll();
        return list;
    }
}
