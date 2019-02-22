package com.iov.common.framework.rest;

import com.iov.common.framework.domain.TrackableEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author hudaowan
 */
@Api(value = "RESTful服务基础接口", description = "RESTful服务基础接口")
public interface AbstractRestServer<Entity extends TrackableEntity> {

    /**
     * 获取所有记录
     *
     * @return JsonViewObject
     */
    @ApiOperation(value = "获取所有记录", notes = "获取所有记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/byAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    JsonViewObject getAll();

    /**
     * 根据条件分页查询记录
     *
     * @param page
     * @return JsonViewObject
     */
    @ApiOperation(value = "根据条件分页查询记录", notes = "根据条件分页查询记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/byPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    JsonViewObject getPage(@ApiParam(value = "查询条件和分页参数", required = true, example = "{\"pageSize\": 10, \"pageNum\": 1}") Page<Entity> page);

    /**
     * 根据条件查询记录
     *
     * @param params 查询条件
     * @return JsonViewObject
     */
    @ApiOperation(value = "根据条件查询记录", notes = "根据条件查询记录", response = JsonViewObject.class, consumes = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/byCondition", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    JsonViewObject getByWhere(@ApiParam(value = "查询条件", required = true) Map<String, Object> params);

    /**
     * 根据id查询记录
     *
     * @param id
     * @return JsonViewObject
     */
    @ApiOperation(value = "根据id查询记录", notes = "根据id查询记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    JsonViewObject getById(@ApiParam(value = "记录的id", required = true, example = "1") @PathParam("id") Integer id);

    /**
     * 根据id删除
     *
     * @param ids
     * @return JsonViewObject
     */
    @ApiOperation(value = "根据多个id删除记录", notes = "根据多个id删除记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/deleting", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    JsonViewObject deleteByIds(@ApiParam(value = "多个记录id，用逗号分隔", required = true, example = "1,2") @QueryParam("ids") String ids);

    /**
     * 新建记录
     *
     * @param entity
     * @return JsonViewObject
     */
    @ApiOperation(value = "新建记录", notes = "新建记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/creating", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    JsonViewObject save(@ApiParam(value = "记录的JSON格式字符串", required = true) Entity entity);

    /**
     * 修改记录
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "修改记录", notes = "修改记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    @RequestMapping(value = "/updating", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    JsonViewObject update(@ApiParam(value = "记录的JSON格式字符串", required = true) Entity entity);
}
