/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.topsem.common.repository.jpa.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-17 上午11:43
 * <p>Version: 1.0
 */
public class QueryException extends NestedRuntimeException {

    public QueryException(String msg) {
        super(msg);
    }

    public QueryException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
