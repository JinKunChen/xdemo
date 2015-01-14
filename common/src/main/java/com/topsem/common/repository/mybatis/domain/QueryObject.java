package com.topsem.common.repository.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;


public class QueryObject {

    Map<String, Object> params = new HashMap<String, Object>();
    private int start; //开始行
    private int limit;  //每页多少条
    private int page = 1;  //第几页
    private int totalElements;
    private String dir;
    private String sort;
    private String searchKey;

    @JsonIgnore
    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = StringUtils.isBlank(searchKey) ? null : searchKey;
    }

    public void addParam(String key, Object value) {
        this.put(key, value);
    }

    public QueryObject put(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public Object getParam(String key) {
        return params.get(key);
    }

    public <T> T getParamByClass(String key) {
        return (T) params.get(key);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

}
