package com.dq.manage.provider;

import com.dq.manage.entity.User;
import com.iov.common.framework.dubbointerface.BaseDubboInterface;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.rest.JsonViewObject;

import java.util.Map;

public interface UserLoginProvider extends BaseDubboInterface<User>{

    JsonViewObject userLogin (Map<String,Object> map) throws DubboProviderException;
}
