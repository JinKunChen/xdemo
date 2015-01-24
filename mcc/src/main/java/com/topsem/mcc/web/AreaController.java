package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.Area;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 地区管理控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/system/areas")
@Slf4j
public class AreaController extends CrudController<Area, Long> {

}
