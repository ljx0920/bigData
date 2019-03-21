package com.tscloud.common.framework.rest.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tscloud.common.framework.Exception.DubboProviderException;
import com.tscloud.common.framework.domain.TrackableEntity;
import com.tscloud.common.framework.dubbo.BaseDubboInterface;
import com.tscloud.common.framework.rest.AbstractRestServerInterface;
import com.tscloud.common.framework.rest.BeanValidationGroups;
import com.tscloud.common.framework.rest.MyValidator;
import com.tscloud.common.framework.rest.view.JsonViewObject;
import com.tscloud.common.framework.rest.view.Page;
import com.tscloud.common.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Context;
import java.util.*;

/**
 * @author daowan.hu
 */
public abstract class AbstractRestServerInterfaceImpl<Entity extends TrackableEntity> implements AbstractRestServerInterface<Entity> {

    protected Logger log = LogManager.getLogger(getClass());

    protected JsonViewObject jsonView = new JsonViewObject();

    @Context
    protected HttpServletResponse response;

    @Context
    protected HttpServletRequest request;

    private String modelName;
    private String systemName;
    protected String userName;

    protected static final String EXISTS = "exists";
    protected static final String TRUE = "true";
    protected static final String FALSE = "false";

    /**
     * 抽象方法，子类需要实现，得到基础服务接口
     *
     * @return 各子类相对应的Provider接口
     */
    public abstract BaseDubboInterface<Entity> getBaseDubboInterface();

    /**
     * {
     * "content": "{"endRowNum":10,"objCondition":null,"pageNumber":1,"pageSize":10,"startRowNum":0,"total":2,"totalPageNum":1,
     * "rows":[
     * {"createDate":"2015-09-29 15:30:42","groupName":"test1","id":"C8879FFFB2064E66BB7A7D4ED052C9DE","userName":null,"webPort":"5052"},
     * {"createDate":"2015-09-29 14:51:46","groupName":"test2","id":"122830C1DC334517BC7BCE79F62D4FD5","userName":null,"webPort":"5052"}
     * ]}",
     * "message": "",
     * "status": "success"
     * }
     *
     * @param page
     * @return
     */
    @Override
    public JsonViewObject getPage(Page page) {
        JSON result;
        String jsonStr = JSON.toJSONString(page);
        System.out.println("getPage方法："+jsonStr);
        try {
            Map<String, Object> mapBean = Maps.newHashMap();
            if (page != null) {
                if (page.getCondition() != null && page.getCondition() instanceof Map) {
                    mapBean = (Map<String, Object>) page.getCondition();
                }
            }
            page = this.getBaseDubboInterface().findByPage(page, mapBean);
            result = (JSON) JSON.toJSON(page);
            jsonView.success(result);
        } catch (DubboProviderException e) {
            jsonView.fail(e);
            log.error("{} getPage error, jsonStr:{}", this.getClass().getSimpleName(), jsonStr, e);
        }
        return jsonView;
    }

    /**
     * {
     * "content": "[
     * {"createDate":"2015-09-29 15:30:42","id":"C8879FFFB2064E66BB7A7D4ED052C9DE","name":"test1","status":"0"},
     * {"createDate":"2015-09-29 14:51:46","id":"122830C1DC334517BC7BCE79F62D4FD5","name":"test2","status":"0"}
     * ]",
     * "message": "",
     * "status": "success"
     * }
     *
     * @return
     */
    @Override
    public JsonViewObject getAll() {
        JSON result;
        try {
            List<Entity> list = this.getBaseDubboInterface().findAll();
            result = (JSON) JSON.toJSON(list);
            jsonView.success(result);
        } catch (DubboProviderException e) {
            jsonView.fail(e);
            log.error("{} getAll error", this.getClass().getSimpleName(), e);
        }
        return jsonView;
    }

    /**
     * {
     * "content": "[
     * {"createDate":"2015-09-29 15:30:42","id":"C8879FFFB2064E66BB7A7D4ED052C9DE","name":"test1","status":"0"},
     * {"createDate":"2015-09-29 14:51:46","id":"122830C1DC334517BC7BCE79F62D4FD5","name":"test2","status":"0"}
     * ]",
     * "message": "",
     * "status": "success"
     * }
     *
     * @param params
     * @return
     */
    @Override
    public JsonViewObject getByWhere(Map<String, Object> params) {
        JSON result;
        String jsonStr = JSON.toJSONString(params);
        try {
            List<Entity> list = this.getBaseDubboInterface().findByMap(params);
            if (list != null) {
                result = (JSON) JSON.toJSON(list);
            } else {
                result = new JSONArray();
            }
            jsonView.success(result);
        } catch (DubboProviderException e) {
            jsonView.fail(e);
            log.error("{} getByWhere error，jsonStr:{}", this.getClass().getSimpleName(), jsonStr, e);
        }
        return jsonView;
    }

    /**
     * {
     * "content": "{"groupId":"D54FAB067D6F47A99136210ED640368E","id":"C8879FFFB2064E66BB7A7D4ED052C9DE","status":"0","webPort":"5052"}",
     * "message": "",
     * "status": "success"
     * }
     *
     * @param id
     * @return JsonViewObject
     */
    @Override
    public JsonViewObject getById(Integer id) {
        JSON result;
        try {
            Entity entity = this.getBaseDubboInterface().findById(id);
            if (entity != null) {
                result = (JSON) JSON.toJSON(entity);
            } else {
                result = new JSONObject();
            }
            jsonView.success(result);
        } catch (DubboProviderException e) {
            jsonView.fail(e);
            log.error("AbstractRestServerInterfaceImpl getById error, id:{}", id, e);
        }
        return jsonView;
    }

    @Override
    public JsonViewObject deleteByIds(String ids) {
        boolean flag;
        int count = 0;
        String[] idArray = ids.split(",");
        try {
            if (idArray.length > 0) {
                for (String id : idArray) {
                    flag = this.getBaseDubboInterface().deleteById(Integer.valueOf(id));
                    if (flag) {
                        count++;
                    }
                }
            }
            if (count > 0) {
                jsonView.success("删除数据成功！");
            } else {
                jsonView.fail("删除数据失败！");
            }
        } catch (DubboProviderException e) {
            jsonView.fail("删除数据失败！" + e.getMessage());
            log.error("AbstractRestServerInterfaceImpl deleteByIds error, id:{}", ids, e);
        }
        return jsonView;
    }

    @Override
    public JsonViewObject save(Entity entity) {
        Set<ConstraintViolation<Entity>> violations = MyValidator.validate(entity, BeanValidationGroups.CreateGroup.class);
        if (violations != null && !violations.isEmpty()) {
            String msg = MyValidator.buildConstraintViolationMessage(violations);
            jsonView.fail(msg);
            log.error("failed to validate request entity while creating: class = {}, msg = {}", entity.getCreateDate(), msg);
            return jsonView;
        }

        String result;
        String jsonStr = JSON.toJSONString(entity);
        try {
            if (entity != null) {
                result = this.getBaseDubboInterface().save(entity);
                if (EXISTS.equals(result)) {
                    jsonView.fail(EXISTS);
                } else {
                    jsonView.success(result);
                }
            }
        } catch (DubboProviderException e) {
            jsonView.fail("保存数据失败！" + e.getMessage());
            log.error("AbstractRestServerInterfaceImpl save error, jsonStr:{}", jsonStr, e);
        }
        return jsonView;
    }

    @Override
    public JsonViewObject update(Entity entity) {
        Set<ConstraintViolation<Entity>> violations = MyValidator.validate(entity, BeanValidationGroups.UpdateGroup.class);
        if (violations != null && !violations.isEmpty()) {
            String msg = MyValidator.buildConstraintViolationMessage(violations);
            jsonView.fail(msg);
            log.error("failed to validate request entity while updating: class = {}, msg = {}", entity.getCreateDate(), msg);
            return jsonView;
        }

        String result;
        String jsonStr = JSON.toJSONString(entity);
        try {
            if (entity != null) {
                result = String.valueOf(this.getBaseDubboInterface().update(entity));
                jsonView.success(result);
            }
        } catch (DubboProviderException e) {
            jsonView.fail("更新数据失败！" + e.getMessage());
            log.error("AbstractRestServerInterfaceImpl update error, jsonStr:{}", jsonStr, e);
        }
        return jsonView;
    }

    /**
     * 添加操作日志
     *
     * @param optContent 操作内容
     * @param param      方法参数
     * @param status     结果状态
     */
/*    @Deprecated
    protected void addOperationLog(String optContent, String param, boolean status) {
        //构建参数JSON
        JSONObject data = new JSONObject();
        //模块名称
        data.put("name", this.modelName);
        //操作内容
        data.put("description", optContent);
        //操作状态
        data.put("status", status);
        //操作人
        data.put("operator", StringUtils.isBlank(userName) ? "admin" : userName);
        data.put("createDate", new Date());
        data.put("param", param);
        LogCache cache = LogCache.init();
        UUID uid = UUID.randomUUID();
        String logKey = systemName + ".sysLog" + uid.toString();
        //写入缓存
        cache.putValue(logKey, data.toJSONString());
    }*/
}
