package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.StoreType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 门店类型管理控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/storeTypes")
@Slf4j
public class StoreTypeController extends CrudController<StoreType, Long> {


}
