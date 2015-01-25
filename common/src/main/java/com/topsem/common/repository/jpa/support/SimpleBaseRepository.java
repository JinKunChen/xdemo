package com.topsem.common.repository.jpa.support;

import com.google.common.collect.Sets;
import com.topsem.common.repository.jpa.BaseRepository;
import com.topsem.common.repository.plugin.entity.LogicDeleteable;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Chen on 14-11-30.
 */
public class SimpleBaseRepository<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    public static final String LOGIC_DELETE_ALL_QUERY_STRING = "update %s x set x.deleted=true where x in (?1)";
    public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x in (?1)";
    public static final String UPDATE_ALL_STATUS_BY_ID_STRING = "update %s x set x.status=%d where x.id in (?1)";

    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ID> entityInformation;


    private Class<T> entityClass;
    private String entityName;
    private String idName;


    public SimpleBaseRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityClass = this.entityInformation.getJavaType();
        this.entityName = this.entityInformation.getEntityName();
        this.idName = this.entityInformation.getIdAttributeNames().iterator().next();
        this.entityManager = entityManager;
    }

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @Override
    public Page<T> findWithPage(Queryable<T> query) {
        return super.findAll(query, query.getPageable());
    }

    @Override
    @Transactional
    public void delete(final ID id) {
        delete(findOne(id));
    }


    /**
     * 删除实体
     *
     * @param entity 实体
     */
    @Override
    public void delete(final T entity) {
        if (entity == null) {
            return;
        }
        if (entity instanceof LogicDeleteable) {
            ((LogicDeleteable) entity).markDeleted();
            save(entity);
        } else {
            super.delete(entity);
            super.flush();
        }
    }


    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    @Transactional
    @Override
    public void delete(final ID[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return;
        }
        List<T> models = new ArrayList<T>();
        for (ID id : ids) {
            T model = null;
            try {
                model = entityClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass + " error", e);
            }
            try {
                BeanUtils.setProperty(model, idName, id);
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass + " error, can not set id", e);
            }
            models.add(model);
        }
        deleteInBatch(models);
    }


    @Transactional
    @Override
    public void deleteInBatch(final Iterable<T> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");

        if (!entities.iterator().hasNext()) {
            return;
        }

        Set models = Sets.newHashSet(entities.iterator());

        boolean isLogicDeleteEntity = LogicDeleteable.class.isAssignableFrom(this.entityClass);

        if (isLogicDeleteEntity) {
            String ql = String.format(LOGIC_DELETE_ALL_QUERY_STRING, entityName);
            executeUpdate(ql, models);
        } else {
            String ql = String.format(DELETE_ALL_QUERY_STRING, entityName);
            executeUpdate(ql, models);
        }
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    @Override
    public T findOneByName(String name) {
        List<T> results = this.findAll(Query.<T>where("name", Query.Filter.Operator.EQ, name));
        return results.isEmpty() ? null : results.get(0);
    }


    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @Transactional
    @Override
    public int updateStatusById(ID id, int status) {
        String ql = String.format(UPDATE_ALL_STATUS_BY_ID_STRING, entityName, status);
        return executeUpdate(ql, new Object[]{id});
    }

    /**
     * 批量更新状态
     *
     * @param entities
     * @param status
     * @return
     */
    @Transactional
    @Override
    public int updateStatusByIdInBatch(final Iterable<ID> entities, int status) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        if (!entities.iterator().hasNext()) {
            return 0;
        }
        String ql = String.format(UPDATE_ALL_STATUS_BY_ID_STRING, entityName, status);
        return executeUpdate(ql, new Object[]{Sets.newHashSet(entities.iterator())});
    }

    /**
     * 执行ql语句
     *
     * @param ql
     * @param params
     * @return
     */
    @Transactional
    @Override
    public int executeUpdate(final String ql, final Object... params) {
        javax.persistence.Query query = entityManager.createQuery(ql);
        setParameters(query, params);
        return query.executeUpdate();
    }

    @Override
    public boolean exists(Specification<T> spec) {
        return findOne(spec) != null;
    }


    /**
     * 按顺序设置Query参数
     *
     * @param query
     * @param params
     */
    private void setParameters(javax.persistence.Query query, Object[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
    }

    /**
     * 根据id更新属性值(支持嵌套类型)
     *
     * @param id  实体Id
     * @param map 如：name=abc,info.user.name=abc
     */
    public void updateById(ID id, Map<String, Object> map) {
        update(super.findOne(id), map);
    }

    /**
     * 根据实体属性值(支持嵌套类型)
     *
     * @param entity 实体
     * @param map    如：name=abc,info.user.name=abc
     */
    public void update(T entity, Map<String, Object> map) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            beanWrapper.setPropertyValue(entry.getKey(), entry.getValue());
        }
        super.saveAndFlush(entity);
    }

}
