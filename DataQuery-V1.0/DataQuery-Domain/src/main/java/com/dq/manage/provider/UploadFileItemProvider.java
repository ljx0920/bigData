package com.dq.manage.provider;

import com.alibaba.fastjson.JSONObject;
import com.dq.manage.entity.UploadFileItem;
import com.iov.common.framework.dubbointerface.BaseDubboInterface;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.rest.JsonViewObject;
import com.iov.common.framework.rest.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author wei.wang5  ${date}
 */
public interface UploadFileItemProvider extends BaseDubboInterface<UploadFileItem> {

    JsonViewObject upLoadFileAndSetParams(Map<String,Object> dataMap);


    Page<UploadFileItem> findByPageLimit(Page<UploadFileItem> page, Map<String, Object> mapBean) throws DubboProviderException;

    JsonViewObject analyisFile(Map<String,Object> fileItemObject);

    JsonViewObject getStatus(Integer[] ids);
}
