package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.mcc.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 订单控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/orders")
@Slf4j
public class OrderController extends CrudController<Order, Long> {

}
