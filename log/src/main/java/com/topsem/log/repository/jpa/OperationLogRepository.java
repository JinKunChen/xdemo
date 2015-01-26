package com.topsem.log.repository.jpa;

import com.topsem.common.repository.jpa.BaseRepository;
import com.topsem.log.domain.OperationLog;

/**
 * Spring Data JPA repository for the OperationLog entity.
 */
public interface OperationLogRepository extends BaseRepository<OperationLog, Long> {


}
