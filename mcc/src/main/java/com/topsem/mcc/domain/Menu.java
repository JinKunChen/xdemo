package com.topsem.mcc.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.topsem.common.domain.Tree;
import com.topsem.common.domain.view.View;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "SYS_MENU")
@ToString(of = "name")
public class Menu extends Tree<Menu> {

    public Menu(Long id) {
        this.id = id;
    }

    @JsonView(View.Public.class)
    private String clazz; // 对应的JavaScript类

    @JsonView(View.Public.class)
    private String parentIds; // 所有父节点路径

    @JsonView(View.Public.class)
    private String hrefTarget; // 请求地址

    @Transient
    @JsonView(View.Checkable.class)
    private boolean checked = false; // 是否被选中

    private String remark; // 备注

}
