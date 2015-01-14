/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.topsem.common.service;

import com.topsem.common.domain.AbstractEntity;
import com.topsem.common.repository.jpa.BaseRepository;
import com.topsem.common.repository.jpa.support.Query;
import com.topsem.common.repository.jpa.support.Queryable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基本服务实现类
 *
 * @param <T>  实体类型
 * @param <ID> 主键ID
 * @auth CHEN
 */
public abstract class BaseService<T extends AbstractEntity, ID extends Serializable> {

    protected BaseRepository<T, ID> baseRepository;

    @Autowired(required = false)
    public void setBaseRepository(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    /**
     * 保存单个实体
     *
     * @param entity 实体
     * @return 返回保存的实体
     */
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    public List<T> save(Iterable<T> entities) {
        return baseRepository.save(entities);
    }

    /**
     * Saves an entity and flushes changes instantly
     *
     * @param entity
     * @return
     */
    public T saveAndFlush(T entity) {
        return baseRepository.saveAndFlush(entity);
    }

    /**
     * 更新单个实体
     *
     * @param entity 实体
     * @return 返回更新的实体
     */
    public T update(T entity) {
        return baseRepository.save(entity);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */

    public void delete(ID id) {
        baseRepository.delete(id);
    }

    /**
     * 删除实体
     *
     * @param entity 实体
     */
    public void delete(T entity) {
        baseRepository.delete(entity);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */

    public void delete(ID[] ids) {
        baseRepository.delete(ids);
    }


    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    public T findOne(ID id) {
        return baseRepository.findOne(id);
    }

    /**
     * 实体是否存在
     *
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    public boolean exists(ID id) {
        return baseRepository.exists(id);
    }

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    public long count() {
        return baseRepository.count();
    }

    /**
     * 根据名称,查实体
     *
     * @return
     */
    public T findOneByName(String name) {
        return baseRepository.findOneByName(name);
    }


    /**
     * 查询所有实体
     *
     * @return
     */
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    /**
     * 按照顺序查询所有实体
     *
     * @param sort
     * @return
     */
    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    /**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return
     */
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    /**
     * 按条件查询实体
     *
     * @param spec 条件
     * @return
     */
    public List<T> findAll(Specification<T> spec) {
        return baseRepository.findAll(spec);
    }

    /**
     * 按条分页并排序查询实体
     *
     * @param queryable 条件
     * @return
     */
    public Page<T> findWithPage(Queryable<T> queryable) {
        return baseRepository.findWithPage(queryable);
    }

    /**
     * 按条件排序查询实体(不分页)
     *
     * @param query
     * @return
     */
    public List<T> findAllWithSort(Query<T> query) {
        return baseRepository.findAll(query, query.getSort());
    }


    /**
     * 按条件统计实体数量
     *
     * @param spec
     * @return
     */
    public Long count(Specification  spec) {
        return baseRepository.count(spec);
    }


    /**
     * 按条件和分页查询
     *
     * @param spec
     * @param pageable
     * @return
     */
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return baseRepository.findAll(spec, pageable);
    }

    public boolean exists(Specification<T> spec) {
        return baseRepository.exists(spec);
    }


    public T findOne(Specification<T> spec) {
        return baseRepository.findOne(spec);
    }

    /**
     * 根据id更新属性值(支持嵌套类型)
     *
     * @param id  实体Id
     * @param map 如：name=abc,info.user.name=abc
     */
    public void updateById(ID id, Map<String, Object> map) {
        baseRepository.updateById(id,map);
    }

    /**
     * 根据实体属性值(支持嵌套类型)
     *
     * @param entity 实体
     * @param map    如：name=abc,info.user.name=abc
     */
    public void update(T entity, Map<String, Object> map) {
        baseRepository.update(entity, map);
    }


}
