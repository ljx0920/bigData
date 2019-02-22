package com.iov.common.framework.mapper;

import com.iov.common.framework.domain.TrackableEntity;

import java.util.List;
import java.util.Map;

/**
 * 持久化接口
 *
 * @param <Entity>
 * @author daowan.hu
 */
public interface BaseInterfaceMapper<Entity extends TrackableEntity> {

    void save(Entity entity);

    void update(Entity entity);

    void deleteById(Integer id);

    Entity findById(Integer id);

    List<Entity> findAll();

    List<Entity> findByMap(Map<String, Object> map);

    Integer getCount(Map<String, Object> map);

    List<Entity> findByPage(Map<String, Object> map);

}
