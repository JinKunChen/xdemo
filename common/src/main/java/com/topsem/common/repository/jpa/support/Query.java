package com.topsem.common.repository.jpa.support;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;


/**
 * JPA通用查询类(条件 + 分页 + 排序)，示例：
 * <pre>
 *     Query.where("name_eq","chen").and("user.age_gt",2).or("email_like","ss").page(1,10).desc("name");
 * </pre>
 *
 * @param <T> 代表结果类型
 * @author CHEN
 */
public class Query<T> implements Queryable<T> {

    private Specification<T> spec;

    private int page = 0;
    private int size = 20;

    private Sort sort;


    public Query() {
    }


    private List<Filter> filters = new ArrayList<Filter>();

    public List<Filter> getFilters() {
        return filters;
    }

    public Filter getFilter(String property) {
        for (Filter filter : filters) {
            if (filter.property.equals(property)) {
                return filter;
            }
        }
        return null;
    }

    /**
     * Creates a new {@link Query} wrapper for the given {@link Specification}.
     *
     * @param spec can be {@literal null}.
     */


    private Query(Specification<T> spec) {
        this.spec = spec;
    }


    /**
     * Simple static factory method to add some syntactic sugar around a {@link Specification}.
     *
     * @param <T>
     * @param spec can be {@literal null}.
     * @return
     */
    public static <T> Query<T> where(Specification<T> spec) {
        return new Query<T>(spec);
    }

    /**
     * Negates the given {@link Specification}.
     *
     * @param <T>
     * @param spec can be {@literal null}.
     * @return
     */
    public static <T> Query<T> not(final Specification<T> spec) {
        return new Query<T>(new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return spec == null ? null : builder.not(spec.toPredicate(root, query, builder));
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaQuery, javax.persistence.criteria.CriteriaBuilder)
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return spec == null ? null : spec.toPredicate(root, query, builder);
    }

    public static <T> Query where(Filter... filters) {
        return new Query<T>().and(Query.<T>specs(filters));
    }


    public static <T> Query where(String property, Filter.Operator operator, Object value) {
        return where(Query.<T>spec(new Filter(property, operator, value)));
    }

    public static <T> Query<T> where(String param, String symbal, String value) {
        return Query.<T>where(Query.Filter.parse(param, value));
    }

    public static <T> Query<T> where(String param, Object value) {
        return Query.<T>where(Query.Filter.parse(param, value));
    }

    public static <T> Query<T> where(String param, String symbol, Object value) {
        return Query.<T>where(param, Query.Filter.Operator.parse(symbol), value);
    }


    public static <T> Query<T> where(Map<String, Object> params) {
        return Query.<T>where(Query.Filter.parse(params).toArray(new Filter[params.size()]));
    }

    /**
     * and 查询调价
     *
     * @param property
     * @param operator
     * @param value
     * @return
     */

    public Query<T> and(String property, Filter.Operator operator, Object value) {
        return and(new Filter(property, operator, value));
    }

    /**
     * and 查询条件 param格式为:user.name_eq
     *
     * @param param
     * @param value
     * @return
     */

    public Query<T> and(String param, Object value) {
        return and(Query.Filter.parse(param, value));
    }


    public Query<T> and(Map<String, Object> params) {
        return and(Query.Filter.parse(params).toArray(new Filter[params.size()]));
    }

    /**
     * and 查询条件(操作符方式)
     *
     * @param param
     * @param symbol 操作符 =, >, <, >=, >=
     * @param value
     * @param <T>
     * @return
     */

    public <T> Query<T> and(String param, String symbol, Object value) {
        return (Query<T>) and(param, Filter.Operator.parse(symbol), value);
    }


    /**
     * and 多查询条件
     *
     * @param specs
     * @return
     */
    public Query<T> and(final Specification<T>... specs) {
        return new Query<T>(new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate thisPredicate = spec == null ? null : spec.toPredicate(root, query, builder);
                Predicate[] predicates = new Predicate[specs.length];
                for (int i = 0; i < specs.length; i++) {
                    predicates[i] = specs[i].toPredicate(root, query, builder);
                }
                return thisPredicate == null ? builder.and(predicates) : builder.and(thisPredicate, builder.and(predicates));
            }
        });
    }


    /**
     * and 查询条件
     *
     * @param filters
     * @return
     */
    public Query<T> and(Filter... filters) {
        this.filters.addAll(Lists.newArrayList(filters));
        return this.and(Query.<T>specs(filters));
    }


    /**
     * or 查询条件
     *
     * @param filters
     * @return
     */
    public Query<T> or(Filter... filters) {
        this.filters.addAll(Lists.newArrayList(filters));
        return this.or(Query.<T>specs(filters));
    }

    /**
     * or 查询条件(操作符方式)
     *
     * @param param
     * @param symbol 操作符 =, >, <, >=, >=
     * @param value
     * @param <T>
     * @return
     */
    public <T> Query<T> or(String param, String symbol, Object value) {
        return (Query<T>) or(param, Filter.Operator.parse(symbol), value);
    }

    /**
     * or 多查询条件
     *
     * @param specs
     * @return
     */
    public Query<T> or(final Specification<T>... specs) {
        return new Query<T>(new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate thisPredicate = spec == null ? null : spec.toPredicate(root, query, builder);
                Predicate[] predicates = new Predicate[specs.length];
                for (int i = 0; i < specs.length; i++) {
                    predicates[i] = specs[i].toPredicate(root, query, builder);
                }
                return thisPredicate == null ? builder.or(predicates) : builder.or(thisPredicate, builder.or(predicates));
            }
        });
    }

    /**
     * Or 查询条件
     *
     * @param property
     * @param operator
     * @param value
     * @return
     */
    public Query<T> or(String property, Filter.Operator operator, Object value) {
        return or(new Filter(property, operator, value));
    }

    /**
     * Or 查询条件 param格式为:user.name_eq
     *
     * @param param
     * @param value
     * @return
     */
    public Query<T> or(String param, Object value) {
        return or(Query.Filter.parse(param, value));
    }

    public Query<T> or(Map<String, Object> params) {
        return or(Query.Filter.parse(params).toArray(new Filter[params.size()]));
    }

    //分页
    public int getPage() {
        return page;
    }

    public Query setPage(int page) {
        this.page = page;
        return this;
    }

    public Query paging(int page, int size) {
        this.page = page;
        this.size = size;
        return this;
    }

    public int getSize() {
        return size;
    }


    public Query setSize(int size) {
        this.size = size;
        return this;
    }

    public Query setPageable(Pageable pageable) {
        this.paging(pageable.getPageNumber(), pageable.getPageSize()).setSort(pageable.getSort());
        return this;
    }

    //排序
    @Override
    public Sort getSort() {
        return sort;
    }

    public Query setSort(Sort sort) {
        this.sort = sort;
        return this;
    }


    public Query orderBy(Sort.Order... orders) {
        this.sort = (this.sort == null ? new Sort(orders) : this.sort.and(new Sort(orders)));
        return this;
    }


    public Query orderBy(String property, Sort.Direction direction) {
        orderBy(new Sort.Order(direction, property));
        return this;
    }


    /**
     * 分组（注意：多个分组要一起写，只识别第一次设置的值)
     *
     * @param properties
     * @return
     */
    public Query groupBy(final String... properties) {
        return new Query<T>(new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Expression[] expressions = new Expression[properties.length];
                for (int i = 0; i < properties.length; i++) {
                    expressions[i] = root.get(properties[i]);
                }
                query.groupBy(expressions);
                return spec == null ? null : spec.toPredicate(root, query, builder);
            }
        });
    }

    public Query distinct() {
        return new Query<T>(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return spec == null ? null : spec.toPredicate(root, query.distinct(true), builder);
            }
        });
    }


    public Query sum(final String... properties) {
        return new Query<T>(new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Selection<Number> s = builder.sum(root.<Number>get(properties[0])).alias("id");
                query.multiselect(s);
                return spec == null ? null : spec.toPredicate(root, query.multiselect(s), builder);
            }
        });
    }

    /**
     * 倒序
     *
     * @param property
     * @return
     */
    public Query desc(String property) {
        orderBy(property, Sort.Direction.DESC);
        return this;
    }

    /**
     * 正序
     *
     * @param property
     * @return
     */

    public Query asc(String property) {
        orderBy(property, Sort.Direction.ASC);
        return this;
    }

    /**
     * 根据属性排序
     *
     * @param property
     * @param direction
     * @return
     */

    public Query orderBy(String property, String direction) {
        orderBy(new Sort.Order(Sort.Direction.fromString(direction), property));
        return this;
    }

    /**
     * 转为分页 pageable
     *
     * @return
     */
    @Override
    public Pageable getPageable() {
        return new PageRequest(page, size, sort);
    }

    /**
     * 查询条件类
     */
    public static class Filter {


        public static enum Operator {

            EQ("等于", "="), NE("不等于", "!="),
            GT("大于", ">"), GTE("大于等于", ">="), LT("小于", "<"), LTE("小于等于", "<="),
            PREFIX_LIKE("前缀模糊匹配", "like"), PREFIX_NOT_LIKE("前缀模糊不匹配", "not like"),
            SUFFIX_LIKE("后缀模糊匹配", "like"), SUFFIX_NOT_LIKE("后缀模糊不匹配", "not like"),
            LIKE("模糊匹配", "like"), NOT_LIKE("不匹配", "not like"),
            IS_NULL("空", "is null"), IS_NOT_NULL("非空", "is not null"),
            IN("包含", "in"), NOT_IN("不包含", "not in"), CUSTOM("自定义默认的", null);

            public final String name;
            public final String symbol;

            Operator(String name, String symbol) {
                this.name = name;
                this.symbol = symbol;
            }

            public static Operator parse(String symbol) {
                for (Operator op : Operator.values()) {
                    if (op.symbol.equalsIgnoreCase(symbol)) {
                        return op;
                    }
                }
                return Operator.CUSTOM;
            }
        }

        public String property;
        public Object value;
        public Operator operator;


        public Filter(String property, Operator operator, Object value) {
            this.property = property;
            this.value = value;
            this.operator = operator;
        }


        /**
         * 字段解析，param格式为FIELDNAME_OPERATOR 例如 name_eq,user.name_like
         *
         * @param param
         * @param value
         * @return
         */
        public static Filter parse(String param, Object value) {
            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(param, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(param + " is not a valid search filter name");
            }
            return new Filter(names[0], Operator.valueOf(names[1].toUpperCase()), value);
        }

        /**
         * 查询参数解析
         * key的格式为FIELDNAME_OPERATOR 例如 name_eq,user.name_like
         */
        public static List<Filter> parse(Map<String, Object> searchParams) {
            List<Filter> filters = new ArrayList<Filter>();
            for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
                filters.add(parse(entry.getKey(), entry.getValue()));
            }
            return filters;
        }
    }


    /**
     * 将Query.Filter，转换为Specification
     *
     * @param filters
     * @param <T>
     * @return
     */
    public static <T> Specification<T>[] specs(final Query.Filter[] filters) {
        Specification<T>[] specs = new Specification[filters.length];
        for (int i = 0; i < filters.length; i++) {
            specs[i] = Query.spec(filters[i]);
        }
        return specs;
    }

    /**
     * 将Query.Filter，转换为Specification
     *
     * @param filter
     * @param <T>
     * @return
     */
    public static <T> Specification<T> spec(final Query.Filter filter) {

        return new Specification<T>() {

            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                // nested path translate, 如Task的名为"user.name"的property, 转换为Task.user.name属性
                String[] names = StringUtils.split(filter.property, ".");
                Path expression = root.get(names[0]);
                for (int i = 1; i < names.length; i++) {
                    expression = expression.get(names[i]);
                }

                // logic operator
                switch (filter.operator) {
                    case EQ:
                        return builder.equal(expression, filter.value);
                    case NE:
                        return builder.notEqual(expression, filter.value);
                    case GT:
                        return builder.greaterThan(expression, (Comparable) filter.value);
                    case LT:
                        return builder.lessThan(expression, (Comparable) filter.value);
                    case GTE:
                        return builder.greaterThanOrEqualTo(expression, (Comparable) filter.value);
                    case LTE:
                        return builder.lessThanOrEqualTo(expression, (Comparable) filter.value);
                    case LIKE:
                        return builder.like(expression, "%" + filter.value + "%");
                    case NOT_LIKE:
                        return builder.notLike(expression, filter.value + "%");
                    case PREFIX_LIKE:
                        return builder.like(expression, "%" + filter.value);
                    case PREFIX_NOT_LIKE:
                        return builder.notLike(expression, "%" + filter.value);
                    case SUFFIX_LIKE:
                        return builder.like(expression, filter.value + "%");
                    case SUFFIX_NOT_LIKE:
                        return builder.notLike(expression, filter.value + "%");
                    case IS_NULL:
                        return expression.isNull();
                    case IN:
                        //当参数为数组时，需要类型转换，以支持可变参数
                        if (filter.value.getClass().isArray()) {
                            return expression.in((Object[]) filter.value);
                        }
                        //当参数为集合类型时，需要类型转换，以支持可变参数
                        if (filter.value instanceof Collection) {
                            return expression.in(((Collection) filter.value).toArray(new Object[]{}));
                        }
                        //当参数为","分隔时，需要类型转换，以支持可变参数
                        if (filter.value instanceof String) {
                            return expression.in(((String) filter.value).split(","));
                        }
                        return expression.in(filter.value);
                }

                return null;
            }
        };
    }
}
