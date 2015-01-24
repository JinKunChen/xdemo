package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 门店管理控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/stores")
@Slf4j
public class StoreController extends CrudController<Store, Long> {

   
}
