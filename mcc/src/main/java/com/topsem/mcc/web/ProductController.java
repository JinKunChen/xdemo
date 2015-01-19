package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 产品控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/products")
@Slf4j
public class ProductController extends CrudController<Product, Long> {

}
