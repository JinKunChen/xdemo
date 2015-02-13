package com.topsem.mcc.web;

import com.topsem.common.repository.jpa.support.Queryable;
import com.topsem.mcc.domain.ComboConfig;
import com.topsem.mcc.service.ComboConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * 系统
 *
 * @author CHEN
 */
@Controller
@RequestMapping("/system")
@Slf4j
public class SystemController {

    @Inject
    private ComboConfigService comboConfigService;

    /**
     * 获取下拉数据
     */
    @RequestMapping("/queryComboData")
    @ResponseBody
    public Object queryComboData(Queryable<ComboConfig> queryable, String queryParam) {
        return comboConfigService.queryComboData(queryable, queryParam);
    }

}
