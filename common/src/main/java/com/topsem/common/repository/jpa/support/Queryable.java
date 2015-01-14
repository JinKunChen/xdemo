package com.topsem.common.repository.jpa.support;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author Chen on 14-11-30.
 */
public interface Queryable<T> extends Specification<T> {
    //排序
    Sort getSort();

    //排序和分页
    Pageable getPageable();

}
