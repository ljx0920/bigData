package com.dq.manage.mapper;

import com.dq.manage.entity.UploadFileItem;
import com.iov.common.framework.mapper.BaseInterfaceMapper;

import java.util.List;

/**
 * @author wei.wang5  ${date}
 */
public interface UploadFileItemMapper extends BaseInterfaceMapper<UploadFileItem>{

    public List<UploadFileItem> findByClassIfyIdGetFileItem(String classIfyId);

}
