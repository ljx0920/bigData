package com.tscloud.common.framework.rest.view;

import com.tscloud.common.framework.Constants;

import java.io.Serializable;

/**
 * restful对外的JSON 对象封装
 *
 * @author daowan.hu
 */
public class JsonViewObject implements Serializable {

    private Object content;
    private String message;
    private String status;

    public Object getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }

    @Deprecated
    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    /**
     * 因在table_template.js文件中，请求deleteByIds方法时，
     * 如果删除成功，需要对status和content同时校验
     * 故，此处增加success方法，用来固定后台删除成功后的调用，
     * 避免前后台数据格式不统一导致前台提示删除失败假象
     *
     * @author vain@ccuu.me
     */
    public JsonViewObject success() {
        return success((Object) "true");
    }

    public JsonViewObject success(Object content) {
        return success(content, "");
    }

    public JsonViewObject success(String message) {
        return success("true", message);
    }

    public JsonViewObject success(Object content, String message) {
        return pack(content, message, Constants.JsonView.STATUS_SUCCESS);
    }

    public JsonViewObject fail() {
        return fail("false", "");
    }

    public JsonViewObject fail(Exception e) {
        String message = e.getMessage();
        if(message==null){
            return success((Object) "true");
        }
        int index = message.indexOf(":");
        return fail(index == -1 ? message : message.substring(index + 1));
    }

    public JsonViewObject fail(String errMsg) {
        return fail("false", errMsg);
    }

    public JsonViewObject fail(Object content, String message) {
        return pack(content, message, Constants.JsonView.STATUS_FAIL);
    }

    private JsonViewObject pack(Object content, String message, String status) {
        this.content = content;
        this.message = message;
        this.status = status;
        return this;
    }
}
