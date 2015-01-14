package com.topsem.common.repository.mybatis;

import com.topsem.common.domain.AbstractEntity;
import com.topsem.common.repository.mybatis.domain.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CrudDao<M extends AbstractEntity> {

    /**
     * 插入记录
     *
     * @param entity
     */
    int insert(M entity);

    /**
     * 根据主键删除记录
     *
     * @param id
     */
    int delete(@Param("id") Serializable id);

    /**
     * 更新记录
     *
     * @param entity
     */
    int update(M entity);

    /**
     * 根据主键取得数据
     *
     * @param id
     * @return entity对象
     */
    M get(@Param("id") Serializable id);

    /**
     * 根据条件查询记录
     *
     * @param queryObject
     * @return 数据列表
     */
    List<M> query(QueryObject queryObject);

    List<M> findAll();

    M findOne();

    /**
     * 查询记录
     *
     * @return 数据列表
     */
    List<Map<String, String>> queryForMap(QueryObject queryObject);

    public int batchInsert(@Param("entities") List<M> entities);

    public int batchUpdate(@Param("entities") List<M> entities);

}
