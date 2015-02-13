package com.topsem.mcc.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.topsem.common.repository.jpa.RepositoryHelper;
import com.topsem.common.repository.jpa.support.Queryable;
import com.topsem.common.service.BaseService;
import com.topsem.mcc.domain.ComboConfig;
import com.topsem.mcc.repository.ComboConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class ComboConfigService extends BaseService<ComboConfig, Long> {

    EntityManager entityManager = RepositoryHelper.getEntityManager();

    @Inject
    private ComboConfigRepository repository;

    public Object queryComboData(final Queryable queryable, String queryParam) {

        //读取下拉配置
        ComboConfig comboConfig = repository.findOne(queryable);
        if (comboConfig == null) {
            return new ArrayList<ComboConfig.ComboData>();
        }

        comboConfig.setQueryParam(queryParam);
        String sql = comboConfig.toSQL();
        log.debug("获取下拉数据..." + sql);

        Query query = entityManager.createNativeQuery(sql);

        Pageable pageable = queryable.getPageable();
        if (pageable == null) {
            return query.getResultList();
        }

        String countSql = comboConfig.toCountSQL();
        Query countQuery = entityManager.createNativeQuery(countSql);
        Long count = ((BigInteger) countQuery.getSingleResult()).longValue();
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Map<String, Object>> comboDatas = Lists.transform(query.getResultList(), new Function() {
            @Nullable
            @Override
            public Map<String, Object> apply(@Nullable Object input) {
                Object[] row = (Object[]) input;
                Map<String, Object> comboData = Maps.newHashMap();
                comboData.put("text", row[0]);
                comboData.put("value", row[1]);
                return comboData;
            }
        });
        return new PageImpl(comboDatas, pageable, count);
    }


}
