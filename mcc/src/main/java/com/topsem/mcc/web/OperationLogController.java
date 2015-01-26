package com.topsem.mcc.web;

import com.topsem.common.web.CrudController;
import com.topsem.log.domain.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 操作日志控制器
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/operationLogs")
@Slf4j
public class OperationLogController extends CrudController<OperationLog, Long> {


}
