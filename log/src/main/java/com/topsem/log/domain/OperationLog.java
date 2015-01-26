package com.topsem.log.domain;

import com.topsem.common.domain.NamedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 普通操作日志（增删改）
 *
 * @author Chen on 15/1/26.
 */
@Entity
@Table(name = "T_OPERATION_LOG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@EqualsAndHashCode(callSuper = false)
public class OperationLog extends NamedEntity {

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作内容
     */
    private String detail;

    @CreatedDate
    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(nullable = false)
    private DateTime createdDate = DateTime.now();

    @Enumerated(EnumType.STRING)
    private Operation operation;

    /**
     * 操作类型
     */
    public enum Operation {
        新增, 删除, 修改
    }
}
