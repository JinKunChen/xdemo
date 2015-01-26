package com.topsem.common.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.topsem.common.domain.IdEntity;
import com.topsem.common.io.excel.ExportExcel;
import com.topsem.common.repository.jpa.support.Queryable;
import com.topsem.common.service.BaseService;
import com.topsem.common.utils.DateUtil;
import com.topsem.common.utils.Reflections;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 增删改查基类
 * <p/>
 * Created by CHEN on 14-6-7.
 */
@Slf4j
public abstract class CrudController<T extends IdEntity, ID extends Serializable> extends BaseController {

    private static final String APPLICATION_JSON_VALUE = "APPLICATION/JSON;CHARSET=UTF-8";

    protected String name;

    @Autowired
    protected BaseService<T, ID> service;

    /*===========ajax方式 start=============*/

    /**
     * 查找
     */
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object ajax_findWithPage(Queryable<T> queryable) {
        return findWithPage(queryable);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public Object ajax_save(T entity) {
        return save(entity);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    @ResponseBody
    public void ajax_delete(@RequestParam(required = true) ID id) {
        service.delete(id);
    }

    /**
     * 根据Id批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "batchDelete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object ajax_deleteInBatch(@RequestParam(value = "ids") ID[] ids) {
        try {
            service.delete(ids);
            return Response.success("删除成功!");
        } catch (Exception e) {
            log.error("删除失败", e);
            return Response.error("删除失败!");
        }
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


    @RequestMapping(value = "/exportExcel",
            method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    @SneakyThrows
    public ResponseEntity exportExcel(Queryable<T> queryable, @RequestParam(defaultValue = "报表数据") String fileName, HttpServletResponse response) {
        Page<T> page = service.findWithPage(queryable);
        //解决ajax中文乱码
        fileName = URLDecoder.decode(fileName, "UTF-8");
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            fileName = fileName + DateUtil.getDate("yyyyMMddHHmmss") + ".xlsx";
        }
        new ExportExcel("报表", Reflections.getClassGenericType(this.getClass(), 0)).setDataList(page.getContent()).write(response, fileName).dispose();
        return new ResponseEntity(HttpStatus.OK);
    }
    /*===========ajax方式 end=============*/


    /*=============Restful方式 start===============*/

    /**
     * 保存实体
     * POST  /rest/authors -> Create a new author.
     *
     * @param model
     * @return
     */
    @RequestMapping(
            method = {RequestMethod.POST, RequestMethod.PUT},
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object save(@RequestBody T model) {
        log.debug("保存实体 : {}", model);
        service.save(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 更新实体(兼容ExtJS)
     *
     * @param id
     * @param json
     * @return
     */
    @RequestMapping(value = "/{id}",
            method = {RequestMethod.POST, RequestMethod.PUT},
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object update(@PathVariable("id") ID id, @RequestBody String json) {
        Map<String, Object> params = JSON.parseObject(json, Map.class);
        Map<String, Object> result = Maps.newHashMap();
        fixNestMapper("", params, result);
        service.updateById(id, result);
        return Response.success("更新成功！");
    }

    /**
     * 转成OGNL形式
     *
     * @param pre
     * @param map
     * @param result
     */
    private void fixNestMapper(String pre, Map<String, Object> map, Map<String, Object> result) {
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            Object m = entry.getValue();
            if (m instanceof Map) {
                fixNestMapper(pre + entry.getKey() + ".", (Map) m, result);
            } else {
                result.put(pre + entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 根据id,获取实体
     * GET  /rest/authors/:id -> get the "id" author.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<T> get(@PathVariable ID id) {
        log.debug("根据ID获取实体 : {}", id);
        T entity = service.findOne(id);
        if (entity == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<T>(entity, HttpStatus.OK);
    }

    /**
     * 根据ID，删除实体
     * DELETE  /rest/authors/:id -> delete the "id" author.
     *
     * @param id
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object delete(@PathVariable ID id) {
        log.debug("根据ID删除实体 : {}", id);
        service.delete(id);
        return Response.success("删除成功!");
    }

    /**
     * GET  /rest/authors/ -> return the page.
     */
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object findWithPage(Queryable<T> queryable) {
        return service.findWithPage(queryable);
    }

    /*=============Restful方式 end===============*/

}
