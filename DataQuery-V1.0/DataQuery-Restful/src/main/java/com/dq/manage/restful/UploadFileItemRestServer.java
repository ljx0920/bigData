package com.dq.manage.restful;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dq.manage.consumer.DataQueryConsumer;
import com.dq.manage.entity.DataManagerConfigure;
import com.dq.manage.entity.UploadFileItem;
import com.dq.manage.provider.DataManagerConfigureProvider;
import com.dq.manage.provider.UploadFileItemProvider;
import com.google.common.collect.Maps;
import com.iov.common.framework.Constants;
import com.iov.common.framework.dubbointerface.BaseDubboInterface;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.rest.AbstractRestServer;
import com.iov.common.framework.rest.JsonViewObject;
import com.iov.common.framework.rest.Page;
import com.iov.common.framework.rest.impl.AbstractRestServerImpl;
import com.iov.common.framework.spring.ServiceBeanContext;
import com.iov.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.stream.Collectors;
import com.dq.manage.restful.UploadClassIfyRestServer;

/**
 * @author wei.wang5
 */
@Api(value = "UploadClassIfyRestServer", description = "数据管理-上传文件数据项")
@RequestMapping(Constants.RestPathPrefix.MANAGE + "upLoadFileItem")
@RestController
public class UploadFileItemRestServer extends AbstractRestServerImpl<UploadFileItem> {

    private static Logger log = org.apache.logging.log4j.LogManager.getLogger(UploadFileItem.class);

    @Override
    public UploadFileItemProvider getBaseDubboInterface() {
        return DataQueryConsumer.getUploadFileItemProvider();
    }

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
        public JsonViewObject uploadFile(@FormDataParam("uploader") String uploader,
                                        @FormDataParam("dataTypeId") String dataTypeId,
                                         @FormDataParam("uploadClassifyId") Integer uploadClassifyId,
                                        @FormDataParam("name1") String name1,
                                        @RequestParam("file") MultipartFile uploadFile) throws DubboProviderException {
        JsonViewObject jsonViewObject = JsonViewObject.newInstance();
        String filePath =   "E://tmp/";

        System.out.println("uploader:"+uploader+"  dataTypeId:"+dataTypeId+"  uploadClassifyId:"+uploadClassifyId+"  name1:"+name1);

        DataManagerConfigureProvider dataManagerConfigureProvider = (DataManagerConfigureProvider) ServiceBeanContext.getBean("dataManagerConfigureProvider");
        String originalFilename = null;
        String serverStoreDir = null;
        String dataTypeName  = null;
        Integer dataTypeRealId = null;
        if(dataTypeId == null || "".equals(dataTypeId)){
            //当dataTypeId为空时说明为一键上传
            String realFileName = uploadFile.getOriginalFilename().split("/")[1];
            originalFilename = realFileName;
            List<DataManagerConfigure> dataManagerConfigureList = dataManagerConfigureProvider.findAll();
            List<DataManagerConfigure> dataManagerFilterList = dataManagerConfigureList.stream().filter(t ->
                    realFileName.contains(t.getDataType())).collect(Collectors.toList());
            if(dataManagerFilterList.size()!=0){
                DataManagerConfigure dataManagerConfigureFilter = dataManagerFilterList.get(0);
                dataTypeRealId = dataManagerConfigureFilter.getId();
                serverStoreDir = dataManagerConfigureFilter.getServerStoreDir() + "/";
                dataTypeName = dataManagerConfigureFilter.getDataType();
            }else{
                return jsonViewObject.fail(originalFilename+"没有找到对应一键上传目录");
            }
        }else {
            //普通上传，有传入的DataTypeId(数据项)
            DataManagerConfigure dataManagerConfigure = dataManagerConfigureProvider.findById(Integer.valueOf(dataTypeId));
            dataTypeRealId = dataManagerConfigure.getId();
            serverStoreDir = dataManagerConfigure.getServerStoreDir() + "/";
            originalFilename = uploadFile.getOriginalFilename();
            dataTypeName = dataManagerConfigure.getDataType();
        }
        long fileSize = uploadFile.getSize();
        File targetFile = new File(filePath+serverStoreDir);
        while (!targetFile.exists()) {
            //   filePath = "E://tmp" + "/" + fileName.split("\\.")[0] + "_" + System.currentTimeMillis() + "." + fileName.split("\\.")[1];
            targetFile.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(filePath + serverStoreDir + originalFilename);
            out.write(uploadFile.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            return jsonViewObject.fail("上传文件失败");
        }
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("dataTypeId",dataTypeRealId);

        if(uploadClassifyId==0){
            System.out.println("进入判断");
            String kmId = new String();
            try
            {
                Thread.currentThread().sleep(2000);//毫秒
            }
            catch(Exception e){}
            try {
                System.out.println("进入try");
                //kmId=this.getBaseDubboInterface().findByDateName(name1);
                UploadClassIfyRestServer upload1 = new UploadClassIfyRestServer();
                kmId = upload1.getBaseDubboInterface().findByDateName(name1);
                System.out.println("判断里的kmId:"+kmId);
            }catch (DubboProviderException e) {
                log.error("AbstractRestServerImpl save error, jsonStr:{}",e);
            }
            uploadClassifyId = Integer.parseInt(kmId);
            dataMap.put("uploadClassifyId",uploadClassifyId);

            //System.out.println("判断里的kmId:"+kmId);
        }
        else {
            dataMap.put("uploadClassifyId",uploadClassifyId);
        }
   //     dataMap.put("uploadClassifyId",uploadClassifyId);
        dataMap.put("dataTypeName",dataTypeName);
        dataMap.put("fileName",originalFilename);
        String format = String.format("%.2f", Double.valueOf(fileSize) / (1024 * 1024));
        dataMap.put("fileSize",format +"Mb");
        dataMap.put("importer",uploader);
        dataMap.put("analytic_progress","未开始");
        dataMap.put("filePath",filePath+serverStoreDir);
        JsonViewObject jsonView = this.getBaseDubboInterface().upLoadFileAndSetParams(dataMap);
        String success = "success";
        if(success.equals(jsonView.getStatus())){
            return jsonViewObject.success("上传文件以及保存数据成功");
        }else {
            return jsonViewObject.fail("上传文件成,保存数据失败");
        }
    }


    @ApiOperation(value = "下载文件", notes = "下载文件")
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public JsonViewObject downloadFile(@ApiParam(value = "下载文件数据项Id", required = true) @RequestParam("fileItemId") Integer fileItemId,
                                        HttpServletRequest request,
                                        HttpServletResponse response)  {
        JsonViewObject jsonView = JsonViewObject.newInstance();
        try {
            UploadFileItem uploadFileItem = this.getBaseDubboInterface().findById(fileItemId);
            String filePath = uploadFileItem.getFilePath();
            String fileName = uploadFileItem.getFileName();
            File file = new File(filePath + fileName);
            if(file.isFile()){
                //下载文件能正常显示中文
                response.setHeader("content-type","application/force-download");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes("utf-8"), "iso8859-1"));
                response.setContentType("application/force-download;charset=UTF-8");
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                OutputStream out = response.getOutputStream();
                int read = bis.read(buffer);
                while (read != -1){
                    out.write(buffer,0 , read);
                    read = bis.read(buffer);
                }
                out.flush();
                out.close();
            }
        } catch (DubboProviderException e) {
            log.error("UploadFileItemRestServer downloadFile error", e);
            return jsonView.fail("下载失败！" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("UploadFileItemRestServer downloadFile error", e);
            return jsonView.fail("下载失败！编码方式异常" + e.getMessage());
        } catch (IOException e) {
            log.error("UploadFileItemRestServer downloadFile error", e);
            return jsonView.fail("下载失败！IO异常" + e.getMessage());
        }
        log.info("UploadFileItemRestServer downloadFile success");
        return jsonView.success("文件下载成功");
    }

    @ApiOperation(value = "下载文件", notes = "下载文件")
    @RequestMapping(value = "/downloadUrl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public JsonViewObject downloadUrl(){
        JsonViewObject jsonView = JsonViewObject.newInstance();
        String downloadAddress = null;
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            downloadAddress = "http://"+hostAddress + ":15082/iov/dq/manage/upLoadFileItem/downloadFile";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return jsonView.success(downloadAddress,"成功");
    }

    @ApiOperation(value = "获取最近更新数据", notes = "获取最近更新数据")
    @RequestMapping(value = "/getRecentItem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public JsonViewObject getRecentItem(@ApiParam(value = "查询条件和分页参数", required = true, example = "{\"pageSize\": 10, \"pageNum\": 1}")Page page){
        JsonViewObject jsonView = JsonViewObject.newInstance();
        String jsonStr = JSON.toJSONString(page);
        try {
            Map<String, Object> mapBean = Maps.newHashMap();
            if (page != null) {
                if (page.getCondition() != null && page.getCondition() instanceof Map) {
                    mapBean = page.getCondition();
                }
            }
            page = this.getBaseDubboInterface().findByPageLimit(page, mapBean);
            jsonView.success(page);
        }catch (Exception e) {
            jsonView.fail(e);
            log.error("{} getRecentItem error, jsonStr:{}", this.getClass().getSimpleName(), jsonStr, e);
        }
        return jsonView;
    }

    @ApiOperation(value = "数据解析", notes = "数据解析", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/analysisFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public JsonViewObject analysisFile(@ApiParam(value = "数据解析", required = true) @RequestBody Map<String,Object> map){
        JsonViewObject jsonView = JsonViewObject.newInstance();
        try {
            if( map.size()!=0){
                jsonView = this.getBaseDubboInterface().analyisFile(map);
            }
        }catch (Exception e) {
            jsonView.fail(e);
            log.error("{} analysisFile error", this.getClass().getSimpleName(), e);
        }
        return jsonView;
    }

    @ApiOperation(value = "获取数据项解析进度", notes = "获取数据项解析进度", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/getStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public JsonViewObject getStatus(@ApiParam(value = "获取数据解析进度", required = true) @RequestBody Integer[] ids){
        JsonViewObject jsonView = JsonViewObject.newInstance();
        try {
            if( ids.length != 0){
                jsonView = this.getBaseDubboInterface().getStatus(ids);
            }
        }catch (Exception e) {
            jsonView.fail(e);
            log.error("{} getStatus error", this.getClass().getSimpleName(), e);
        }
        return jsonView;
    }
}
