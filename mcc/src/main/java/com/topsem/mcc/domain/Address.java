package com.topsem.mcc.domain;

import com.topsem.common.domain.NamedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 地址实体
 *
 * @author Chen on 15/1/19.
 */

@Entity
@Table(name = "T_ADDRESS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@EqualsAndHashCode(callSuper = false)
public class Address extends NamedEntity {

    @ManyToOne
    @JoinColumn(name = "areaId")
    private Area area;

}