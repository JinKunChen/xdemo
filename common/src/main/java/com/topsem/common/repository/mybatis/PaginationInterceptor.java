package com.topsem.common.repository.mybatis;

import com.topsem.common.repository.mybatis.domain.Page;
import com.topsem.common.repository.mybatis.domain.QueryObject;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import javax.xml.bind.PropertyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Mybatis - 通用分页拦截器v3.2.0
 *
 * @author liuzh/abel533/isea533
 *         Created by liuzh on 14-4-15.
 * @url http://git.oschina.net/free/Mybatis_PageHelper
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PaginationInterceptor implements Interceptor {
    private static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();

    private static final List<ResultMapping> EMPTY_RESULT_MAPPING = new ArrayList<ResultMapping>(0);

    private static String dialect = ""; //数据库方言

    /**
     * 开始分页
     *
     * @param pageNum
     * @param pageSize
     */
    public static void startPage(int pageNum, int pageSize) {
        startPage(pageNum, pageSize, true);
    }

    /**
     * 开始分页
     *
     * @param pageNum
     * @param pageSize
     */
    public static void startPage(int pageNum, int pageSize, boolean count) {
        localPage.set(new Page(pageNum, pageSize, count ? Page.SQL_COUNT : Page.NO_SQL_COUNT));
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        Object parameterObject = args[1];

        if (!(parameterObject instanceof QueryObject)) {
            return invocation.proceed();
        }

        QueryObject queryObject = (QueryObject) parameterObject;

        if (queryObject.getLimit() <= 0) {
            return invocation.proceed();
        }

        //忽略RowBounds-否则会进行Mybatis自带的内存分页
        args[2] = RowBounds.DEFAULT;
        MappedStatement ms = (MappedStatement) args[0];
        BoundSql boundSql = ms.getBoundSql(queryObject);

        MappedStatement qs = newMappedStatement(ms, new BoundSqlSqlSource(boundSql));
        //将参数中的MappedStatement替换为新的qs，防止并发异常
        args[0] = qs;
        MetaObject msObject = SystemMetaObject.forObject(qs);
        String sql = (String) msObject.getValue("sqlSource.boundSql.sql");
        //简单的通过total的值来判断是否进行count查询
        //求count - 重写sql
        msObject.setValue("sqlSource.boundSql.sql", getCountSql(sql));
        //查询总数
        Object result = invocation.proceed();
        int totalCount = (Integer) ((List) result).get(0);
        queryObject.setTotalElements(totalCount);

        //分页sql - 重写sql
        msObject.setValue("sqlSource.boundSql.sql", getPageSql(sql, queryObject));
        //恢复类型
        msObject.setValue("resultMaps", ms.getResultMaps());
        //执行分页查询
        return invocation.proceed();

    }

    /**
     * 获取总数sql - 如果要支持其他数据库，修改这里就可以
     *
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        return "select count(0) from (" + sql + ") tmp_count";
    }

    /**
     * 获取分页sql - 如果要支持其他数据库，修改这里就可以
     *
     * @param sql
     * @param query
     * @return
     */
    private String getPageSql(String sql, QueryObject query) {
        StringBuilder pageSql = new StringBuilder(200);
        if ("mysql".equals(dialect)) {
            pageSql.append(sql);
            pageSql.append(" limit " + query.getStart() + "," + query.getLimit());
        } else if ("hsqldb".equals(dialect)) {
            pageSql.append(sql);
            pageSql.append(" LIMIT " + query.getLimit() + " OFFSET " + query.getStart());
        } else if ("oracle".equals(dialect)) {
            pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
            pageSql.append(sql);
            pageSql.append(" ) temp where rownum <= ").append(query.getPage() * query.getLimit());
            pageSql.append(") where row_id > ").append(query.getStart());
        }
        return pageSql.toString();
    }

    /**
     * 由于MappedStatement是一个全局共享的对象，因而需要复制一个对象来进行操作，防止并发访问导致错误
     *
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "_分页", newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuffer keyProperties = new StringBuffer();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        //由于resultMaps第一次需要返回int类型的结果，所以这里需要生成一个resultMap - 防止并发错误
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), int.class, EMPTY_RESULT_MAPPING).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    /**
     * 只拦截Executor
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (dialect != null && dialect.equals("")) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
    }

    private class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
