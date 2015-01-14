package com.topsem.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.topsem.common.annotation.FieldDefine;
import com.topsem.common.domain.view.View;
import com.topsem.common.io.excel.annotation.ExcelField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;

/**
 * 统一定义name的entity基类.
 * <p/>
 *
 * @author CHEN
 */

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class NamedEntity extends IdEntity {

    @ExcelField(title = "名称", align = 2, sort = 20)
    @FieldDefine(title = "名称", SN = 20)
    @JsonView(View.NameOnly.class)
    protected String name;

}
