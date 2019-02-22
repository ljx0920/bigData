package com.dq.manage.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dq.manage.entity.DataManagerConfigure;
import com.dq.manage.entity.RequestParam;
import com.dq.manage.entity.UploadFileItem;
import com.dq.manage.mapper.DataManagerConfigureMapper;
import com.dq.manage.mapper.UpLoadClassIfylMapper;
import com.dq.manage.mapper.UploadFileItemMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iov.common.framework.dubbointerface.impl.BaseDubboInterfaceImpl;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.mapper.BaseInterfaceMapper;
import com.iov.common.framework.rest.JsonViewObject;
import com.iov.common.framework.rest.Page;
import com.iov.common.utils.HttpUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import com.dq.manage.provider.UpLoadClassIfyProviderImpl;

/**
 * @author wei.wang5  ${date}
 */
public class UploadFileItemProviderImpl extends BaseDubboInterfaceImpl<UploadFileItem> implements UploadFileItemProvider{


    @Autowired
    @Qualifier("uploadFileItemMapper")
    private UploadFileItemMapper uploadFileItemMapper;
    public UpLoadClassIfylMapper upLoadClassIfylMapper;
    @Autowired
    @Qualifier("dataManagerConfigureMapper")
    DataManagerConfigureMapper dataManagerConfigureMapper;

    private static Logger log = org.apache.logging.log4j.LogManager.getLogger(UploadFileItemProviderImpl.class);


    @Override
    public String findByDateName(String name) throws DubboProviderException{
       System.out.println("进入findByDateName方法");
        return upLoadClassIfylMapper.findByDateName(name);

        //return null;
    }

    @Override
    public UploadFileItemMapper getBaseInterfaceMapper() {
        return uploadFileItemMapper;
    }


    @Override
    public JsonViewObject upLoadFileAndSetParams(Map<String,Object> dataMap) {
        UploadFileItem uploadFileItem = JSON.parseObject(JSON.toJSONString(dataMap), UploadFileItem.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        uploadFileItem.setImportTime(simpleDateFormat.format(new Date()));
        uploadFileItemMapper.save(uploadFileItem);
        return jsonView.success("保存数据成功");
    }

    @Override
    public Page<UploadFileItem> findByPageLimit(Page<UploadFileItem> page, Map<String, Object> map) throws DubboProviderException{
        try {
            //取前50条数据
            page.setTotal(this.getBaseInterfaceMapper().getCount(map));
            page = Page.newInstance(page.getPageSize(), page.getTotal(), page.getPageNum());
            map.put("startRowNum", page.getStartRowNum());
            map.put("pageSize", page.getPageSize());
            List<UploadFileItem> byPageList = this.getBaseInterfaceMapper().findByPage(map);
            page.setRows(byPageList);
        } catch (Exception e) {
            log.error("UploadFileItemProviderImpl findByPageLimit ", e);
            throw new DubboProviderException(e.getMessage(), e);
        }
        return page;
    }

    @Override
    public JsonViewObject analyisFile(Map<String,Object> fileItemObject) {
        String fileItemId = fileItemObject.get("fileItemId").toString();
        UploadFileItem uploadFileItem = this.getBaseInterfaceMapper().findById(Integer.valueOf(fileItemId));
        uploadFileItem.setAnalyticProgress("正在解析");
        this.getBaseInterfaceMapper().update(uploadFileItem);
        //1.先拷贝文件到ftp文件目录
        Integer dataTypeId = uploadFileItem.getDataTypeId();
        DataManagerConfigure dataManagerConfigure = dataManagerConfigureMapper.findById(dataTypeId);
        String executeTarget = dataManagerConfigure.getExecuteTarget();
        String serverStoreDir = dataManagerConfigure.getServerStoreDir();
        String filePath = uploadFileItem.getFilePath();
        String fileName = uploadFileItem.getFileName();
        try {
            int byteSum = 0;
            int byteRead = 0;
            File oldFile = new File(filePath+fileName);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(filePath+fileName);
                String targetFilePath = "E://copytmp/"+serverStoreDir+"/";
                File targetFile = new File(targetFilePath);
                //不存在路径就创建目标路径
                while (!targetFile.exists()){
                    targetFile.mkdirs();
                }
                FileOutputStream fs = new FileOutputStream(targetFilePath+fileName);
                byte[] buffer = new byte[1444];
                while ( (byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead;
                    fs.write(buffer, 0, byteRead);
                    log.info("成功拷贝数据");
                }
                inStream.close();
                fs.close();
            }
        }
        catch (Exception e) {
            log.error("复制文件数据到{}失败","E://copytmp/"+serverStoreDir+"/"+fileName);
            return jsonView.fail("拷贝文件失败");
        }
        //2.调用大数据平台的数据处理接口，等待接口返回批次号。
        String requestUrl = executeTarget;
        List list = Lists.newArrayList();
        fileItemObject.remove("fileItemId");
        for (String key :fileItemObject.keySet()){
            Map<String, Object> requestParam = Maps.newHashMap();
            requestParam.put("name",key);
            requestParam.put("value",fileItemObject.get(key).toString());
            requestParam.put("type","");
            requestParam.put("operation","");
            list.add(requestParam);
        }
        //去平台请求数据处理批次号地址
        String batchNumObeject = HttpUtils.doPost(requestUrl, JSON.toJSONString(list));
        JSONObject jsonObject = JSON.parseObject(batchNumObeject);
        String batchNum = jsonObject.get("content").toString();
        log.info("成功获取数据处理批次号，为{}",batchNum);
        uploadFileItem.setBatchNumber(batchNum);
        this.getBaseInterfaceMapper().update(uploadFileItem);
        return jsonView.success("成功获取批次号");
    }

    @Override
    public JsonViewObject getStatus(Integer[] ids) {
        JSONArray allStatus = new JSONArray();
        for (Integer id: ids) {
            UploadFileItem uploadFileItem = this.getBaseInterfaceMapper().findById(id);
            String batchNumber = uploadFileItem.getBatchNumber();
            JSONObject jsonObject = new JSONObject();
            if("0".equals(batchNumber)){
                String analyticProgress = uploadFileItem.getAnalyticProgress();
                jsonObject.put("id",id);
                jsonObject.put("analyticProgress",analyticProgress);
                allStatus.add(jsonObject);
            }else {
                //调用大数据平台的接口，返回解析状态,需要传递的参数为 数据处理id和批次号。
                Integer dataTypeId = uploadFileItem.getDataTypeId();
                DataManagerConfigure dataManagerConfigure = dataManagerConfigureMapper.findById(dataTypeId);
                String executeTarget = dataManagerConfigure.getExecuteTarget();
                //切分数据处理得到url的 IP、数据处理Id
                String[] url = executeTarget.split("/");
                List<String> urlList = Arrays.asList(url);
                String dataProcessingId = urlList.get(urlList.size()-1);
                String ipAndPort = urlList.get(2);
                String requestUrl = ipAndPort +"/iov/rest/dataprocessing/dataprocessing/taskStatus?dataProcessingId="+dataProcessingId+"&dpBatch="+batchNumber;
                String analyticProgress = uploadFileItem.getAnalyticProgress();
                if("正在解析".equals(analyticProgress)){
                    String statusString = HttpUtils.doGet(requestUrl);
                    JSONObject statusObject = JSON.parseObject(statusString);
                    String status = statusObject.get("content").toString();
                    //1.代表正在解析状态（正在执行状态）
                    if("1".equals(status)){
                        uploadFileItem.setAnalyticProgress("正在解析");
                    }else if ("2".equals(status)){
                        uploadFileItem.setAnalyticProgress("解析完成");
                    }else if ("0".equals(status)){
                        uploadFileItem.setAnalyticProgress("未开始");
                    }
                    this.getBaseInterfaceMapper().update(uploadFileItem);
                    analyticProgress = status;
                }
                jsonObject.put("id",id);
                jsonObject.put("analyticProgress",analyticProgress);
                allStatus.add(jsonObject);
            }
        }
        return jsonView.success(allStatus,"获取状态成功");
    }

    /**
     * 需要重写删除方法，删除数据之前也需要吧该条下的文件数据删除
     * @param id
     * @return
     * @throws DubboProviderException
     */
    @Override
    public boolean deleteById(Integer id) throws DubboProviderException{
        UploadFileItem uploadFileItem = uploadFileItemMapper.findById(id);
        String filePath = uploadFileItem.getFilePath();
        String fileName = uploadFileItem.getFileName();
        //删除实际数据
        File file = new File(filePath + fileName);
        if(file.isFile()){
            file.delete();
        }
        super.deleteById(id);
        return true;
    }
}
