package com.topsem.common.web.method.support;

import com.topsem.common.mapper.JsonMapper;
import com.topsem.common.repository.jpa.support.Query;
import com.topsem.common.repository.jpa.support.Queryable;
import com.topsem.common.utils.Reflections;
import com.topsem.common.web.method.annotation.QueryableDefault;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;


/**
 * 请求查询参数字符串及分页/排序参数绑定到Queryable
 * <pre>
 *     查询参数格式如下：
 *        http://xxx?q.name_like=chen&&q.customer.id_in=1,2,3&&q.age_lt=12&&page=2&size=100&sort=email,asc
 *     1、
 *        控制器处理方法写法(默认分页并且排序)
 *        public Object query(Queryable queryable);
 *
 *     2、
 *        控制器处理方法写法(不分页,只排序)
 *        @QueryableDefault(needPage = false)
 *        public Object query(Queryable queryable);
 *
 *     3、
 *        控制器处理方法写法(只分页,不排序)
 *        @QueryableDefault(needSort = false)
 *        public Object query(Queryable queryable);
 *
 *     4、
 *        默认查询字符串(不分页,不排序)
 *        @QueryableDefault(needPage = false, needSort = false)
 *        public Object query(Queryable queryable);
 * </pre>
 *
 * @author Chen
 */

@Slf4j
public class QueryableMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

    private static final String QUERY_PREFIX = "q.";
    private static final String __OR__ = "__OR__";
    private static final String __AND__ = "__AND__";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Queryable.class.equals(parameter.getParameterType());
    }

    @Data
    static class ExtJsOrder {
        private String property;
        private String direction;

        public Sort toSort() {
            return new Sort(Sort.Direction.fromString(direction), property);
        }
    }

//    public static void main(String[] args) {
//        String s = "[{\"property\":\"name\",\"direction\":\"ASC\"}]";
//        ExtJsOrder[] sorts = JsonMapper.nonDefaultMapper().fromJson(s, ExtJsOrder[].class);
//        System.out.println(sorts);
//    }

    @Override
    public Queryable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                     NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {


        //扩展查询接口
        QueryableDefault queryableDefault = getQueryableDefaultFromAnnotation(methodParameter);
        boolean needPage = queryableDefault == null ? true : queryableDefault.needPage();
        boolean needSort = queryableDefault == null ? true : queryableDefault.needSort();

        Class clazz = Reflections.getClassGenericType(methodParameter.getContainingClass(), 0);
        Query query = getQuery(webRequest.getParameterMap(), clazz);
        //不分页，不排序
        if (!needPage && !needSort) {
            return query.paging(-1, -1).setSort(null);
        }

        Pageable pageable = pageableResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        //兼容ExtJS->limit分页参数
        if (webRequest.getParameter("limit") != null) {
            size = Integer.valueOf(webRequest.getParameter("limit"));
            page = page <= 0 ? 0 : page - 1; //从0开始计算页码
        }
        //只分页，不排序
        if (needPage && !needSort) {
            return query.paging(page, size).setSort(null);
        }

        //兼容ExtJS->sort排序 ==> &sort=[{"property":"name","direction":"ASC"}]
        Sort sort = pageable.getSort();
        String sortParam = webRequest.getParameter("sort");
        if (sortParam != null && sortParam.startsWith("[{")) {
            ExtJsOrder[] extJsOrders = JsonMapper.nonDefaultMapper().fromJson(sortParam, ExtJsOrder[].class);
            sort = extJsOrders[0].toSort();
            for (int i = 1; i < extJsOrders.length; i++) {
                sort = sort.and(extJsOrders[i].toSort());
            }
        }

        //只排序，不分页
        if (needSort && !needPage) {
            return query.setSort(sort).paging(-1, -1);
        }

        //要分页，也排序
        return query.paging(page, size).setSort(sort);
    }

    /**
     * 获取查询参数(支持类型转换失败,将自动过滤)
     * "user.name_eq = 1"  ==> "user.name = 1 "
     * "user.name_eq__OR__user.age_gt = 1"  ==> "user.name = 1 or user.age > 1"
     * "user.name_eq__AND__user.age_gt = 1"  ==> "user.name = 1 and  user.age > 1"
     * "user.name_eq__AND__user.age_gt = abc"  ==> "user.name = abc" user.age 被过滤
     * "user.name_eq__AND__user.age_gt__OR__user.id_gt = abc"  ==> "user.name = abc " user.age,user.id被过滤
     *
     * @param parameterMap
     * @return
     */
    @SneakyThrows
    private Query getQuery(Map<String, String[]> parameterMap, Class clazz) {
        BeanWrapper wrapper = new BeanWrapperImpl(clazz.newInstance());
        wrapper.setAutoGrowNestedPaths(true); //自动创建嵌套类型
        Query query = new Query();
        Map<String, Object> attributes = getAttributeNames(parameterMap);
        for (String key : attributes.keySet()) {
            Query tempQuery = new Query();
            Object value = attributes.get(key);
            for (String p : key.split(__OR__)) {
                if (p.contains(__AND__)) {
                    Query tempAndQuery = new Query();
                    for (String andKey : p.split(__AND__)) {
                        andKey = fixAndCheckType(andKey, value, wrapper);
                        if (andKey != null) {
                            tempAndQuery = tempAndQuery.and(andKey, value);
                        }
                    }
                    tempQuery = tempQuery.or(tempAndQuery);
                } else {
                    p = fixAndCheckType(p, value, wrapper);
                    if (p != null) {
                        tempQuery = tempQuery.or(p, value);
                    }
                }
            }
            query = key.endsWith(__OR__) ? query.or(tempQuery) : query.and(tempQuery);
        }
        return query;
    }

    /**
     * 修正并检查类型（兼容 name ==> name_eq）
     *
     * @param key
     * @param value
     * @param wrapper
     * @return
     */
    private String fixAndCheckType(String key, Object value, BeanWrapper wrapper) {
        if (!StringUtils.isEmpty(key)) {
            key = key.contains("_") ? key : (key + "_eq");
            if(wrapper.getWrappedClass() == Object.class){
                return key;
            }
            try {
                String temp = key.substring(0, key.indexOf("_"));
                wrapper.setPropertyValue(temp, value);
                return key;
            } catch (Exception e) {
                log.warn("类型检查不通过，将被忽略：" + key);
            }
        }
        return null;
    }

    /**
     * 截取取查询参数
     * "q.user.name = 1"  ==> "user.name = 1"
     *
     * @param parameterMap
     * @return
     */
    private Map<String, Object> getAttributeNames(Map<String, String[]> parameterMap) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : parameterMap.keySet()) {
            if (key.startsWith(QUERY_PREFIX)) {
                String property = key.substring(QUERY_PREFIX.length(), key.length());
                //去除空格
                String[] values = StringUtils.trimArrayElements(parameterMap.get(key));
                String value = StringUtils.arrayToDelimitedString(values, ",");
                //过滤掉空值
                if (!StringUtils.isEmpty(value)) {
                    result.put(property, value);
                }
            }
        }
        return result;
    }


    /**
     * 获取默认查询配置
     *
     * @param parameter
     * @return
     */
    private QueryableDefault getQueryableDefaultFromAnnotation(MethodParameter parameter) {
        //首先从参数上找
        QueryableDefault queryableDefault = parameter.getParameterAnnotation(QueryableDefault.class);
        //找不到从方法上找
        if (queryableDefault == null) {
            queryableDefault = parameter.getMethodAnnotation(QueryableDefault.class);
        }
        return queryableDefault;
    }
}
