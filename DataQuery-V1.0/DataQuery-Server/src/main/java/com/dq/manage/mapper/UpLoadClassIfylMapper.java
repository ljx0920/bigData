package com.dq.manage.mapper;

import com.dq.manage.entity.UploadClassify;
import com.dq.manage.entity.UploadFileItem;
import com.iov.common.framework.mapper.BaseInterfaceMapper;

import java.util.List;

/**
 * @author wei.wang5  ${date}
 */
public interface UpLoadClassIfylMapper extends BaseInterfaceMapper<UploadClassify>{

    public List<UploadClassify> findByUpLoadClassIfyId(String classIfyId);

    String findByDateName(String name);
}
