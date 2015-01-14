package com.topsem.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

/**
 * 通用帐号类
 *
 * @author Chen on 14-11-28.
 */

@Data
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class AbstractAccount extends NamedEntity {
    private String password;
}


