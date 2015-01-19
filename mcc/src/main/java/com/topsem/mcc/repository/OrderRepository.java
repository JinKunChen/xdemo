package com.topsem.mcc.repository;

import com.topsem.common.repository.jpa.BaseRepository;
import com.topsem.mcc.domain.Order;

/**
 * Spring Data JPA repository for the Order entity.
 */
public interface OrderRepository extends BaseRepository<Order, Long> {



}
