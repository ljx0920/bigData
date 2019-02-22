package com.iov.common.framework.dubbointerface;

import com.iov.common.framework.domain.TrackableEntity;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.rest.Page;

import java.util.List;
import java.util.Map;

/**
 * dubbo抽象接口，  所有dubbo的接口都需要继承
 *
 * @param <Entity>
 * @author daowan.hu
 */
public interface BaseDubboInterface<Entity extends TrackableEntity> {

    /**
     * 分页查询
     *
     * @param page
     * @param map
     * @return
     * @throws DubboProviderException
     */
    Page findByPage(Page page, Map<String, Object> map) throws DubboProviderException;

    /**
     * 持久化对象的信息
     *
     * @param entity
     * @return
     * @throws DubboProviderException
     */
    String save(Entity entity) throws DubboProviderException;

    /**
     * 修改对象的信息
     *
     * @param entity
     * @return
     * @throws DubboProviderException
     */
    boolean update(Entity entity) throws DubboProviderException;

    /**
     * 根据ID 删除指定的对象
     *
     * @param id
     * @return
     * @throws DubboProviderException
     */
    boolean deleteById(Integer id) throws DubboProviderException;

    /**
     * 根据ID 查找指定的对象
     *
     * @param id
     * @return
     * @throws DubboProviderException
     */
    Entity findById(Integer id) throws DubboProviderException;

    /**
     * 得到所有的对象
     *
     * @return
     * @throws DubboProviderException
     */
    List<Entity> findAll() throws DubboProviderException;

    /**
     * 用于多条件查询
     *
     * @param map
     * @return
     * @throws DubboProviderException
     */
    List<Entity> findByMap(Map<String, Object> map) throws DubboProviderException;

    String findByDateName(String name) throws DubboProviderException;
}
