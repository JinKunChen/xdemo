package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 产品分类控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/productTypes")
@Slf4j
public class ProductTypeController extends CrudController<ProductType, Long> {

}
