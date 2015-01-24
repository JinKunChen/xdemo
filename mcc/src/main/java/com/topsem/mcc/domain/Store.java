package com.topsem.mcc.domain;

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
 * 门店实体
 *
 * @author Chen on 15/1/19.
 */

@Entity
@Table(name = "T_STORE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@EqualsAndHashCode(callSuper = false)
public class Store extends NamedEntity {

    /**
     * 门店类型
     */
    @ManyToOne
    @JoinColumn(name = "storeTypeId")
    private StoreType storeType;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    @CreatedDate
    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(nullable = false)
    private DateTime createdDate = DateTime.now();
}
