package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.Brand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 品牌控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/brands")
@Slf4j
public class BrandController extends CrudController<Brand, Long> {

}
