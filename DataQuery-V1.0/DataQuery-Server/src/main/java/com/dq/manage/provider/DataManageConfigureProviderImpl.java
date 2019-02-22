package com.dq.manage.provider;

import com.dq.manage.entity.DataManagerConfigure;
import com.dq.manage.mapper.DataManagerConfigureMapper;
import com.iov.common.framework.dubbointerface.impl.BaseDubboInterfaceImpl;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.mapper.BaseInterfaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author wei.wang5
 */
public class DataManageConfigureProviderImpl extends BaseDubboInterfaceImpl<DataManagerConfigure> implements DataManagerConfigureProvider {

    @Override
    public String findByDateName(String name) throws DubboProviderException {
        return null;
    }

    @Autowired
    @Qualifier("dataManagerConfigureMapper")
    private DataManagerConfigureMapper dataManagerConfigureMapper;


    @Override
    public BaseInterfaceMapper<DataManagerConfigure> getBaseInterfaceMapper() {
        return dataManagerConfigureMapper;
    }
}
