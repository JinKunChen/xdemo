package com.topsem.mcc.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.topsem.common.domain.view.View;
import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.Area;
import com.topsem.mcc.service.AreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * 地区管理控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/system/areas")
@Slf4j
public class AreaController extends CrudController<Area, Long> {

    @Inject
    AreaService areaService;

    /**
     * 获得菜单树
     */
    @ResponseBody
    @RequestMapping("/getTree")
    @JsonView({View.WithChildren.class})
    @Transactional
    public Object getTree(@RequestParam Long id) {
        Area area = areaService.findOne(id);
        if (area == null) {
            return "[]";
        }
        area.setChildren(areaService.findByParentId(id));
        return area;
    }
}
