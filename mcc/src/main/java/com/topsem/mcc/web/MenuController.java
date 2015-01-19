package com.topsem.mcc.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.topsem.common.domain.view.View;
import com.topsem.common.web.CrudController;
import com.topsem.common.web.Response;
import com.topsem.mcc.domain.Menu;
import com.topsem.mcc.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 系统菜单
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/system/menus")
@Slf4j
public class MenuController extends CrudController<Menu, Long> {

    @Autowired
    private MenuService menuService;

    /**
     * 删除菜单
     */
    @RequestMapping(value = "/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object delete(@PathVariable Long id) {
        if (!menuService.getMenuByParentId(id).isEmpty()) {
            return new Response(false, "请先删除该菜单下的所有子菜单！");
        }
        menuService.delete(id);
        return Response.success("删除菜单成功！");
    }

    /**
     * 获得accordion菜单列表
     */
    @ResponseBody
    @RequestMapping("/getMenuTreeList")
    public Object getMenuTreeList() {
        log.info("加载系统菜单...");
        return menuService.getMenuByParentId(0L);
    }

    /**
     * 获得菜单树
     */
    @ResponseBody
    @RequestMapping("/getTree")
    @JsonView({View.WithChildren.class})
    public Object getTree(@RequestParam Long id) {
        Menu menu = menuService.findOne(id);
        if (menu == null) {
            return "[]";
        }
        menu.setChildren(menuService.getMenuByParentId(id));
        return menu;
    }

    /**
     * 获得菜单树
     */
    @ResponseBody
    @RequestMapping("/getCheckableTree")
    @JsonView({View.CheckableAndWithChildren.class})
    public Object getCheckableTree(@RequestParam Long id) {
        Menu menu = menuService.findOne(id);
        if (menu == null) {
            return "[]";
        }
        menu.setChildren(menuService.getMenuByParentId(id));
        return menu;
    }
}
