package com.topsem.common.web.handler;

import com.topsem.common.service.ServiceException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chen on 14-7-30.
 */
public class DefaultExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);

        // 根据不同错误转向不同页面
        if (ex instanceof ServiceException) {
            return new ModelAndView("error-business", model);
        } else {
            return new ModelAndView("error", model);
        }
    }
}
