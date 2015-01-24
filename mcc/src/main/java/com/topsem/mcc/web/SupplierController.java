package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 供应商管理控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/suppliers")
@Slf4j
public class SupplierController extends CrudController<Supplier, Long> {


}
