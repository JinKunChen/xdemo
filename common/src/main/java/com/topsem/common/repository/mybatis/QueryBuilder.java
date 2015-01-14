package com.topsem.common.repository.mybatis;

import com.google.common.base.Throwables;
import com.topsem.common.repository.mybatis.domain.QueryObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by CHEN on 2014/6/7.
 */
public class QueryBuilder {

    private static List<String> fieldNames = new ArrayList<String>();

    static {
        Field[] fields = QueryObject.class.getDeclaredFields();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
    }

    public static QueryObject from(HttpServletRequest request) {
        QueryObject queryObject = new QueryObject();
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String name = en.nextElement();
            if (StringUtils.isNotEmpty(name)) {
                String value = StringUtils.join(request.getParameterValues(name), ",");
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
