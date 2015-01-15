package com.topsem.mcc.repository;

import com.topsem.mcc.domain.PersistentAuditEvent;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, String> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByPrincipalAndEventDateAfter(String principal, LocalDateTime after);

    List<PersistentAuditEvent> findAllByEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
