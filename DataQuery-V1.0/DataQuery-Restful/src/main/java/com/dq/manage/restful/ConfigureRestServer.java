package com.dq.manage.restful;

import com.dq.manage.consumer.DataQueryConsumer;
import com.dq.manage.entity.DataManagerConfigure;
import com.dq.manage.entity.Node;
import com.dq.manage.provider.DataManagerConfigureProvider;
import com.iov.common.framework.Constants;
import com.iov.common.framework.dubbointerface.BaseDubboInterface;
import com.iov.common.framework.rest.JsonViewObject;
import com.iov.common.framework.rest.impl.AbstractRestServerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author wei.wang5  ${date}
 */
@Api(value = "ConfigureRestServer", description = "数据管理-配置中心")
@RequestMapping(Constants.RestPathPrefix.MANAGE + "configure")
@RestController
public class ConfigureRestServer extends AbstractRestServerImpl<DataManagerConfigure> {

    private static Logger log = LogManager.getLogger(ConfigureRestServer.class);

    @Override
    public DataManagerConfigureProvider getBaseDubboInterface() {
        return DataQueryConsumer.getDataManagerConfigureProvider();
    }


}
