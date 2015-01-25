package com.topsem.mcc.service;

import com.topsem.common.service.BaseService;
import com.topsem.mcc.domain.Area;
import com.topsem.mcc.repository.AreaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service class for managing orders.
 */
@Service
@Transactional
@Slf4j
public class AreaService extends BaseService<Area, Long> {

    @Inject
    AreaRepository areaRepository;

    public List<Area> findByParentId(Long id) {
        return areaRepository.findByParentId(id);
    }
}
