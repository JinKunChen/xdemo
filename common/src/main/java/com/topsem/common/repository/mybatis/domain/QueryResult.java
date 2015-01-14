package com.topsem.common.repository.mybatis.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class QueryResult<T> {

    private int totalElements;
    private List<T> content;

    public QueryResult() {
    }

    public QueryResult(List<T> content) {
        int total = content.size();
        if (content instanceof Page) {
            total = ((Page) content).getTotal();
        }
        this.totalElements = total;
        this.content = content;
    }

    public QueryResult( int total,List<T> content) {
        this.totalElements = total;
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int total) {
        this.totalElements = total;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}