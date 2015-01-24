package com.topsem.mcc.domain;

import com.topsem.common.domain.BaseTree;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 地区实体
 *
 * @author Chen on 15/1/25.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "T_AREA")
@ToString(of = "name")
public class Area extends BaseTree<Area> {

    /**
     * 地区编码
     */
    @Column(nullable = true)
    private Integer code;

}
