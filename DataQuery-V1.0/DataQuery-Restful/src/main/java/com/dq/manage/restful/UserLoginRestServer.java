package com.dq.manage.restful;


import com.dq.manage.consumer.DataQueryConsumer;
import com.dq.manage.entity.User;
import com.dq.manage.provider.UserLoginProvider;
import com.iov.common.framework.Constants;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.rest.JsonViewObject;
import com.iov.common.framework.rest.impl.AbstractRestServerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.Map;

@Api(value = "UserLoginRestServer", description = "用户登录")
@RequestMapping(Constants.RestPathPrefix.MANAGE + "login")
@RestController
public class UserLoginRestServer extends AbstractRestServerImpl<User> {


    private static Logger log = LogManager.getLogger(UserLoginRestServer.class);

    @Override
    public UserLoginProvider getBaseDubboInterface() {
        return DataQueryConsumer.getUserLoginProvider();
    }

    @ApiOperation(value = "数据管理与应用系统登录", notes = "数据管理与应用系统登录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    JsonViewObject userLogin( @ApiParam(value = "用户登录名以及密码", required = true)@RequestBody Map<String, Object> map) {
        JsonViewObject jsonViewObject = JsonViewObject.newInstance();
        try {
            if(map.size()!= 0) {
                jsonViewObject = getBaseDubboInterface().userLogin(map);
            }
        } catch (DubboProviderException e) {
            jsonViewObject.fail(e);
            log.error("TemplateRestServer byPanelId error", e);
        }
        return jsonViewObject;
    }

}
