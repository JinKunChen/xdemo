/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.topsem.common.repository.jpa;


import com.topsem.common.repository.jpa.support.Queryable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>抽象DAO层基类 提供一些简便方法<br/>
 *
 * @param <T>
 * @param <ID>
 * @auth Chen
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    Page<T> findWithPage(Queryable<T> query);


    /**
     * 根据名称，获取所有记录数
     *
     * @param name
     * @return
     */
    public T findOneByName(String name);


    /**
     * 根据条件，判断是否存在
     *
     * @param spec
     * @return
     */
    boolean exists(Specification<T> spec);

    /**
     * 根据主键删除
     *
     * @param ids
     */
    public void delete(ID[] ids);

    /**
     * 根据id更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public int updateStatusById(ID id, int status);


    /**
     * 批量更新状态
     *
     * @param entities
     * @param status
     * @return
     */
    public int updateStatusByIdInBatch(Iterable<ID> entities, int status);


    @Transactional
    int executeUpdate(String ql, Object... params);

    /**
     * 根据id更新属性值(支持嵌套类型)
     *
     * @param id  实体Id
     * @param map 如：name=abc,info.user.name=abc
     */
    public void updateById(ID id, Map<String, Object> map);

    /**
     * 根据实体属性值(支持嵌套类型)
     *
     * @param entity 实体
     * @param map    如：name=abc,info.user.name=abc
     */
    public void update(T entity, Map<String, Object> map);

}
