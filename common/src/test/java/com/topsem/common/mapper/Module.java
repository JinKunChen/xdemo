package com.topsem.common.mapper;

import com.topsem.common.annotation.FieldDefine;
import com.topsem.common.domain.NamedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Chen on 14-12-8.
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Module extends NamedEntity {

    @FieldDefine(title = "描述", hidden = true, SN = 10)
    private String description;

    @FieldDefine(title = "图标", hidden = true, SN = 10)
    private String icon;

    public Module(String name, String description, String icon) {
        this.name = name;
        this.icon = icon;
        this.description = description;
    }


}
