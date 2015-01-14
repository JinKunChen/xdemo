package com.topsem.common.web;

import com.alibaba.fastjson.JSON;
import com.topsem.common.service.ServiceException;
import com.topsem.common.web.util.WebUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Chen on 14-7-30.
 */
public class BaseController {

    /**
     * 基于@ExceptionHandler异常处理
     */
    @ExceptionHandler({Exception.class, ServiceException.class})
    public ModelAndView exp(HttpServletRequest request, HttpServletResponse response,
                            Exception ex) throws IOException {


        // 根据不同错误转向不同页面
        if (ex instanceof ServiceException) {
            //如果是ajax请求,返回Json格式错误信息
            if (WebUtils.isAjaxRequest(request)) {
                response.getWriter().write(JSON.toJSONString(new Response(false, ex.getMessage())));
                response.getWriter().close();
                return null;
            }
        }

        ex.printStackTrace();

        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.addObject("errorCode","Error " + request.getAttribute("javax.servlet.error.status_code"));
        modelAndView.addObject("errorMessage",getErrorMessage(request));
        return modelAndView;
    }


    public String  getErrorMessage(HttpServletRequest request) {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<ul>");
        while (throwable != null) {
            errorMessage.append("<li>").append(escapeTags(throwable.getMessage())).append("</li>");
            throwable = throwable.getCause();
        }
        errorMessage.append("</ul>");
       return errorMessage.toString();
    }

    /**
     * Substitute 'less than' and 'greater than' symbols by its HTML entities.
     */
    private String escapeTags(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }




}
