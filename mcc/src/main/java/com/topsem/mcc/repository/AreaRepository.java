package com.topsem.mcc.repository;

import com.topsem.common.repository.jpa.BaseRepository;
import com.topsem.mcc.domain.Area;

import java.util.List;

/**
 * Spring Data JPA repository for the Area entity.
 */
public interface AreaRepository extends BaseRepository<Area, Long> {

    List<Area> findByParentId(Long id);

}
