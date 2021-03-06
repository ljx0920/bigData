package com.tscloud.common.framework.dubbo.impl;

import com.tscloud.common.framework.Exception.DubboProviderException;
import com.tscloud.common.framework.domain.TrackableEntity;
import com.tscloud.common.framework.dubbo.BaseDubboInterface;
import com.tscloud.common.framework.mapper.BaseInterfaceMapper;
import com.tscloud.common.framework.rest.view.JsonViewObject;
import com.tscloud.common.framework.rest.view.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author daowan.hu
 */
public abstract class BaseDubboInterfaceImpl<Entity extends TrackableEntity> implements BaseDubboInterface<Entity> {

    protected static Logger log = LogManager.getLogger(BaseDubboInterfaceImpl.class);

    protected JsonViewObject jsonView = new JsonViewObject();
    public static final String EXISTS = "exists";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    @Override
    public Page findByPage(Page page, Map<String, Object> map) throws DubboProviderException {
        try {
            page.setTotal(this.getBaseInterfaceMapper().getCount(map));
            map.put("startRowNum", page.getStartRowNum());
            map.put("pageSize", page.getPageSize());
            page.setRows(this.getBaseInterfaceMapper().findByPage(map));
        } catch (Exception e) {
            log.error("BaseDubboInterfaceImpl findByPage ", e);
            throw new DubboProviderException(e.getMessage(), e);
        }
        return page;
    }

    @Override
    public String save(Entity entity) throws DubboProviderException {
        try {
            entity.setCreateDate(new Date());
            this.getBaseInterfaceMapper().save(entity);
        } catch (Exception e) {
            log.error("BaseDubboInterfaceImpl save ", e);
            throw new DubboProviderException(e.getMessage(), e);
        }
        return String.valueOf(entity.getId());
    }

    @Override
    public boolean update(Entity entity) throws DubboProviderException {
        // FIXME 应该使用update语句返回的更新数据条数，而不是flag来判断更新是否成功
        boolean flag = true;
        try {
            entity.setCreateDate(new Date());
            this.getBaseInterfaceMapper().update(entity);
        } catch (Exception e) {
            flag = false;
            log.error("BaseDubboInterfaceImpl update ", e);
            throw new DubboProviderException(e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public boolean deleteById(Integer id) throws DubboProviderException {
        // FIXME 应该使用delete语句返回的删除数据条数，而不是flag来判断删除是否成功
        boolean flag = true;
        try {
            this.getBaseInterfaceMapper().deleteById(id);
        } catch (Exception e) {
            flag = false;
            log.error("BaseDubboInterfaceImpl deleteById ", e);
            throw new DubboProviderException(e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public Entity findById(Integer id) throws DubboProviderException {
        Entity entity;
        try {
            entity = this.getBaseInterfaceMapper().findById(id);
        } catch (Exception e) {
            log.error("BaseDubboInterfaceImpl findById ", e);
            throw new DubboProviderException(e.getMessage(), e);
        }
        return entity;
    }

    @Override
    public List<Entity> findAll() throws DubboProviderException {
        List<Entity> list;
        try {
            list = this.getBaseInterfaceMapper().findAll();
        } catch (Exception e) {
            log.error("BaseDubboInterfaceImpl findAll ", e);
            throw new DubboProviderException(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<Entity> findByMap(Map<String, Object> map) throws DubboProviderException {
        List<Entity> list;
        try {
            list = this.getBaseInterfaceMapper().findByMap(map);
        } catch (Exception e) {
            log.error("BaseDubboInterfaceImpl findByMap", e);
            throw new DubboProviderException(e.getMessage(), e);
        }
        return list;
    }

    /**
     * 抽象方法需要实现，得到基础服务接口
     *
     * @return
     */
    public abstract BaseInterfaceMapper<Entity> getBaseInterfaceMapper();
}
