package com.topsem.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.topsem.common.annotation.FieldDefine;
import com.topsem.common.domain.view.View;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义id的entity基类.
 * <p/>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 *
 * @author CHEN
 */
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class IdEntity extends AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.IdOnly.class)
    @FieldDefine(title = "ID号", SN = 10)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
