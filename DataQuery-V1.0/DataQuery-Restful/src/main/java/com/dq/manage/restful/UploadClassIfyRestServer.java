package com.dq.manage.restful;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dq.manage.consumer.DataQueryConsumer;
import com.dq.manage.entity.Node;
import com.dq.manage.entity.UploadClassify;
import com.dq.manage.provider.UpLoadClassIfyProvider;
import com.iov.common.framework.Constants;
import com.iov.common.framework.dubbointerface.BaseDubboInterface;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.rest.AbstractRestServer;
import com.iov.common.framework.rest.JsonViewObject;
import com.iov.common.framework.rest.impl.AbstractRestServerImpl;
import com.iov.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dq.manage.provider.UpLoadClassIfyProvider;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author wei.wang5  ${date}
 */
@Api(value = "UploadClassIfyRestServer", description = "数据管理-上传分类树")
@RequestMapping(Constants.RestPathPrefix.MANAGE + "upLoadTree")
@RestController
public class UploadClassIfyRestServer extends AbstractRestServerImpl<UploadClassify> {

    private static Logger log = LogManager.getLogger(UploadClassIfyRestServer.class);

    @Override
    public UpLoadClassIfyProvider getBaseDubboInterface() {
        return DataQueryConsumer.getUpLoadClassIfyProvider();
    }


    @ApiOperation(value = "获取上传的分类树", notes = "获取上传的分类树")
    @RequestMapping(value = "/getUploadTree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public JsonViewObject getUploadTree() {
        JsonViewObject jsonViewObject = JsonViewObject.newInstance();
        try {
            List<Node> deptTree = this.getBaseDubboInterface().getNodes(null);
            jsonViewObject.success(deptTree, "获取机构树成功");
        } catch (Exception e) {
            jsonViewObject.fail("false", "获取机构树失败!" + e.getMessage());
            log.error("UploadClassIfyRestServer getDeptTree is error", e);
        }
        return jsonViewObject;
    }

    @ApiOperation(value = "根据Id删除上传分类树", notes = "根据Id删除上传分类树")
    @RequestMapping(value = "/deletingUploadTree",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public JsonViewObject deletingUploadTree(@ApiParam(value = "deleteNode") @RequestBody String ids) {
        boolean flag = true;
        boolean ff = false;
        int count = 0;
        JsonViewObject jsonView = JsonViewObject.newInstance();
        try {
            String idsString = JSON.parseObject(ids).getString("ids");
            if (!StringUtils.isBlank(idsString)) {
                String[] idArray = idsString.split(",");
                for (String id : idArray) {
                    //验证分类下是否有数据，是否可以删除
                    ff = this.getBaseDubboInterface().check(id);
                    if (ff) {
                        flag = this.getBaseDubboInterface().deleteById(Integer.valueOf(id));
                        if (!flag) {
                            continue;
                        } else {
                            count++;
                        }
                    } else {
                        count = 0;
                    }
                }
            }
            if (count > 0) {
                jsonView.success("true", "删除数据成功!");
            } else {
                jsonView.fail("false", "删除数据失败，该分类下有数据");
            }
        } catch (Exception e) {
            log.error("PlatDictionaryTypeRestServer deleteByIds is error,{Id:" + ids + "}", e);
        }
        return jsonView;
    }

    @ApiOperation(value = "新建树", notes = "新建树")
    @RequestMapping(value = "/creatTree",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public JsonViewObject creatTree(@RequestBody UploadClassify uploadClassify){
        JsonViewObject jsonView = JsonViewObject.newInstance();
        String jsonStr = JSON.toJSONString(uploadClassify);
        System.out.println("creatTree传入的json:"+jsonStr);
        JSONObject jsonObj = JSONObject.parseObject(jsonStr);
        String name = jsonObj.getString("name");
        String date = jsonObj.getString("date");
        String name1 = jsonObj.getString("name1");
//        System.out.println("date:"+date);
//        System.out.println("name1:"+name1);

        //上传第一层节点
        UploadClassify uploadKt = new UploadClassify();
        uploadKt.setName(name);
        uploadKt.setParentId("0");
        try {
            this.getBaseDubboInterface().save(uploadKt);
        } catch (DubboProviderException e) {
            jsonView.fail("上传分类树数据失败！" + e.getMessage());
            log.error("AbstractRestServerImpl save error, jsonStr:{}", jsonStr, e);
        }

        //上传第二层节点
        String ktId = new String();
        try {
            ktId=this.getBaseDubboInterface().findByDateName(name);
           // System.out.println("课题id:"+ktId);
        }catch (DubboProviderException e) {
            jsonView.fail("上传分类树数据失败！" + e.getMessage());
            log.error("AbstractRestServerImpl save error, jsonStr:{}", jsonStr, e);
        }

        UploadClassify uploadDate = new UploadClassify();
        uploadDate.setName(date);
        uploadDate.setParentId(ktId);
        try{
            this.getBaseDubboInterface().save(uploadDate);
        }
        catch (DubboProviderException e){
            jsonView.fail("上传分类树数据失败！" + e.getMessage());
            log.error("AbstractRestServerImpl save error, jsonStr:{}", jsonStr, e);
        }

        //上传第三层节点
//        String dataId = new String();
//        try {
//            dataId=this.getBaseDubboInterface().findByDateName(date);
//            System.out.println("日期id:"+dataId);
//        }catch (DubboProviderException e) {
//            jsonView.fail("上传分类树数据失败！" + e.getMessage());
//            log.error("AbstractRestServerImpl save error, jsonStr:{}", jsonStr, e);
//        }
        int dateId = Integer.parseInt(ktId)+1;
        String dateId1 = String.valueOf(dateId);
        UploadClassify uploadKm = new UploadClassify();
        uploadKm.setName(name1);
        uploadKm.setParentId(dateId1);
        try{
            this.getBaseDubboInterface().save(uploadKm);
        }
        catch (DubboProviderException e){
            jsonView.fail("上传分类树数据失败！" + e.getMessage());
            log.error("AbstractRestServerImpl save error, jsonStr:{}", jsonStr, e);
        }

        return jsonView;
    }
}
