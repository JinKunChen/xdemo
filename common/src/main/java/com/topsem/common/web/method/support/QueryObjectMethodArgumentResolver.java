package com.topsem.common.web.method.support;

import com.google.common.base.Throwables;
import com.topsem.common.repository.mybatis.domain.QueryObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Chen on 14-6-17.
 */
public class QueryObjectMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String QUERY_PREFIX = "q.";

    private static List<String> fieldNames = new ArrayList<String>();

    static {
        Field[] fields = QueryObject.class.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return QueryObject.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        QueryObject queryObject = new QueryObject();
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String name = entry.getKey();
            if (StringUtils.isNotEmpty(name)) {
                if(name.startsWith(QUERY_PREFIX)){
                    name = name.substring(QUERY_PREFIX.length(), name.length());
                    name = name.substring(0, name.indexOf("_"));
                }
                String value = StringUtils.join(entry.getValue(), ",");
                if (fieldNames.contains(name)) {
                    try {
                        BeanUtils.setProperty(queryObject, name, value);
                    } catch (Exception e) {
                        Throwables.propagate(e);
                    }
                } else {
                    queryObject.put(name, value);
                }
            }
        }
        return queryObject;
    }
}
