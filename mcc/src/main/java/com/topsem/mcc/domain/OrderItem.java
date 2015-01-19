package com.topsem.mcc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topsem.common.domain.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 订单项实体
 *
 * @author Chen on 15/1/19.
 */

@Entity
@Table(name = "T_ORDER_ITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderItem extends IdEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;

    //数量
    private int amount;

}
