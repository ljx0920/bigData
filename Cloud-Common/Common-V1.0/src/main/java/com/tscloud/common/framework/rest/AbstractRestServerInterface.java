package com.tscloud.common.framework.rest;

import com.tscloud.common.framework.domain.TrackableEntity;
import com.tscloud.common.framework.rest.view.JsonViewObject;
import com.tscloud.common.framework.rest.view.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * @author hudaowan
 */
@Api(value = "RESTful服务基础接口", description = "RESTful服务基础接口")
public interface AbstractRestServerInterface<Entity extends TrackableEntity> {

    /**
     * 获取所有记录
     *
     * @return JsonViewObject
     */
    @GET
    @Path("/byAll")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "获取所有记录", notes = "获取所有记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    JsonViewObject getAll();

    /**
     * 根据条件分页查询记录
     *
     * @param page
     * @return JsonViewObject
     */
    @POST
    @Path("/byPage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "根据条件分页查询记录", notes = "根据条件分页查询记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    JsonViewObject getPage(@ApiParam(value = "查询条件和分页参数", required = true, example = "{\"pageSize\": 10, \"pageNum\": 1}") Page page);

    /**
     * 根据条件查询记录
     *
     * @param params 查询条件
     * @return JsonViewObject
     */
    @POST
    @Path("/byCondition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "根据条件查询记录", notes = "根据条件查询记录", response = JsonViewObject.class, consumes = MediaType.APPLICATION_JSON)
    JsonViewObject getByWhere(@ApiParam(value = "查询条件", required = true) Map<String, Object> params);

    /**
     * 根据id查询记录
     *
     * @param id
     * @return JsonViewObject
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "根据id查询记录", notes = "根据id查询记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    JsonViewObject getById(@ApiParam(value = "记录的id", required = true, example = "1") @PathParam("id") Integer id);

    /**
     * 根据id删除
     *
     * @param ids
     * @return JsonViewObject
     */
    @GET
    @Path("/deleting")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "根据多个id删除记录", notes = "根据多个id删除记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON)
    JsonViewObject deleteByIds(@ApiParam(value = "多个记录id，用逗号分隔", required = true, example = "1,2") @QueryParam("ids") String ids);

    /**
     * 新建记录
     *
     * @param entity
     * @return JsonViewObject
     */
    @POST
    @Path("/creating")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "新建记录", notes = "新建记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    JsonViewObject save(@ApiParam(value = "记录的JSON格式字符串", required = true) Entity entity);

    /**
     * 修改记录
     *
     * @param entity
     * @return
     */
    @POST
    @Path("/updating")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "修改记录", notes = "修改记录", response = JsonViewObject.class, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    JsonViewObject update(@ApiParam(value = "记录的JSON格式字符串", required = true) Entity entity);
}
