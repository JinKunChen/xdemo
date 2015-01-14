package com.topsem.common.service;

import com.topsem.common.domain.AbstractEntity;
import com.topsem.common.repository.mybatis.CrudDao;
import com.topsem.common.repository.mybatis.domain.QueryObject;
import com.topsem.common.repository.mybatis.domain.QueryResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CHEN on 14-6-7.
 */
public abstract class CrudService<M extends AbstractEntity> {


    @Autowired
    protected CrudDao<M> dao;

    /**
     * 插入记录
     *
     * @param entity
     */
    public int insert(M entity) {
        return dao.insert(entity);
    }

    /**
     * 保存记录，根据主键判断是否为新纪录
     *
     * @param entity
     * @return
     */

    public int save(M entity) {
        return (null == entity.getId() || (entity.getId() instanceof Number && (Integer) entity.getId() == 0)) ? dao.insert(entity) : dao.update(entity);
    }


    /**
     * 根据主键删除记录
     *
     * @param id
     */
    public int delete(@Param("id") Serializable id) {
        return dao.delete(id);
    }

    /**
     * 更新记录
     *
     * @param entity
     */
    public int update(M entity) {
        return dao.update(entity);

    }

    /**
     * 根据主键取得数据
     *
     * @param id
     * @return entity对象
     */
    public M get(@Param("id") Serializable id) {
        return dao.get(id);
    }

    /**
     * 根据条件查询记录
     *
     * @param queryObject
     * @return 数据列表
     */
    public QueryResult<M> query(QueryObject queryObject) {
        List<M> settings = dao.query(queryObject);
        return new QueryResult<M>(queryObject.getTotalElements(), settings);
    }

    public List<M> findAll() {
        return dao.findAll();
    }

    public M findOne() {
        return dao.findOne();
    }

}

