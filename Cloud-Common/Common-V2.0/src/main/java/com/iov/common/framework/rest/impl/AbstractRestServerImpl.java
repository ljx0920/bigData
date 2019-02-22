package com.iov.common.framework.rest.impl;

import com.alibaba.fastjson.JSON;
import com.iov.common.framework.domain.TrackableEntity;
import com.iov.common.framework.dubbointerface.BaseDubboInterface;
import com.iov.common.framework.exception.DubboProviderException;
import com.google.common.collect.Maps;
import com.iov.common.framework.rest.*;import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author daowan.hu
 */
public abstract class AbstractRestServerImpl<Entity extends TrackableEntity> implements AbstractRestServer<Entity> {

    protected static Logger log = LogManager.getLogger(AbstractRestServerImpl.class);

    @Context
    protected HttpServletResponse response;

    @Context
    protected HttpServletRequest request;

    protected static final String EXISTS = "exists";
    protected static final String TRUE = "true";
    protected static final String FALSE = "false";
    protected static final String BLANK_JSON_STRING = "{}";

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
    public JsonViewObject getPage(@RequestBody Page<Entity> page) {
        JsonViewObject jsonView = JsonViewObject.newInstance();
        String jsonStr = JSON.toJSONString(page);
        try {
            Map<String, Object> mapBean = Maps.newHashMap();
            if (page != null) {
                if (page.getCondition() != null && page.getCondition() instanceof Map) {
                    mapBean = page.getCondition();
                }
            }
            page = this.getBaseDubboInterface().findByPage(page, mapBean);
            jsonView.success(page);
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
        JsonViewObject jsonView = JsonViewObject.newInstance();
        try {
            List<Entity> list = this.getBaseDubboInterface().findAll();
            jsonView.success(list);
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
    public JsonViewObject getByWhere(@RequestBody Map<String, Object> params) {
        JsonViewObject jsonView = JsonViewObject.newInstance();
        String jsonStr = JSON.toJSONString(params);
        try {
            List<Entity> list = this.getBaseDubboInterface().findByMap(params);
            jsonView.success(list);
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
    public JsonViewObject getById(@PathVariable Integer id) {
        JsonViewObject jsonView = JsonViewObject.newInstance();
        try {
            Entity entity = this.getBaseDubboInterface().findById(id);
            jsonView.success(entity);
        } catch (DubboProviderException e) {
            jsonView.fail(e);
            log.error("AbstractRestServerImpl getById error, id:{}", id, e);
        }
        return jsonView;
    }

    @Override
    public JsonViewObject deleteByIds(String ids) {
        JsonViewObject jsonView = JsonViewObject.newInstance();
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
            log.error("AbstractRestServerImpl deleteByIds error, id:{}", ids, e);
        }
        return jsonView;
    }

    @Override
    public JsonViewObject save(@RequestBody Entity entity) {
        JsonViewObject jsonView = JsonViewObject.newInstance();
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
            log.error("AbstractRestServerImpl save error, jsonStr:{}", jsonStr, e);
        }
        return jsonView;
    }

    @Override
    public JsonViewObject update(@RequestBody Entity entity) {
        JsonViewObject jsonView = JsonViewObject.newInstance();
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
            log.error("AbstractRestServerImpl update error, jsonStr:{}", jsonStr, e);
        }
        return jsonView;
    }
}
