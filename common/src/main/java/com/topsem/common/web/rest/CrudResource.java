package com.topsem.common.web.rest;

import com.topsem.common.domain.AbstractEntity;
import com.topsem.common.repository.jpa.support.Query;
import com.topsem.common.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * REST 增删改查
 *
 * @author Chen on 14-10-21.
 */
@Slf4j
public abstract class CrudResource<M extends AbstractEntity, ID extends Serializable> {

    public final String APPLICATION_JSON_VALUE = "APPLICATION/JSON;CHARSET=UTF-8";

    @Autowired
    protected BaseService<M, ID> service;

    /**
     * POST  /rest/authors -> Create a new author.
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = APPLICATION_JSON_VALUE)

    public M save(@RequestBody M model) {
        log.debug("保存实体 : {}", model);
        return service.save(model);
//        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * GET  /rest/authors/:id -> get the "id" author.
     */
    @RequestMapping(value = "/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<M> get(@PathVariable ID id) {
        log.debug("根据ID获取实体 : {}", id);
        M entity = service.findOne(id);
        if (entity == null) {
            return new ResponseEntity<M>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<M>(entity, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/authors/:id -> delete the "id" author.
     */
    @RequestMapping(value = "/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable ID id) {
        log.debug("根据ID删除实体 : {}", id);
        service.delete(id);
    }


    /**
     * GET  /rest/authors/page -> return the page.
     */
    @RequestMapping(value = "/query",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object findWithPage(Query query) {
        return service.findWithPage(query);
    }

    /**
     * DELETE  /rest/authors/delete -> delete the "ids" author.
     */
    @RequestMapping(value = "/delete",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity deleteIds(@RequestBody ID[] ids) {
        service.delete(ids);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateById(@RequestBody ID id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            map.put(entry.getKey(), StringUtils.arrayToDelimitedString(entry.getValue(), ","));
        }
        service.updateById(id, map);
        return new ResponseEntity(HttpStatus.OK);
    }

}
