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
 * 商品实体
 *
 * @author Chen on 15/1/19.
 */
@Entity
@Table(name = "T_PRODUCT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends NamedEntity {

    /**
     * 所属品牌
     */
    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    /**
     * 商品单价
     */
    private double price;

    /**
     * 商品型号
     */
    private String model;

    /**
     * 图片地址
     */
    private String imgUrl;


    /**
     * 商品描述
     */
    private String description;
}
