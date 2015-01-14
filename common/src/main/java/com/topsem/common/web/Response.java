package com.topsem.common.web;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * Created by CHEN on 2014/6/7.
 */
public class Response {


    private boolean success;
    private Object msg;

    private Map<String, Object> errors = Maps.newHashMap();

    public Response(boolean success) {
        this(success, "");
    }

    public Response(boolean success, Object msg) {
        setSuccess(success);
        setMsg(msg);
    }

    public static Response success(Object msg) {
        return new Response(true, msg);
    }

    public static Response error(String msg) {
        return new Response(false, msg);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Response addError(String name, Object message) {
        errors.put(name, message);
        return this;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
