package com.tscloud.common.framework.mapper;

import com.tscloud.common.framework.domain.TrackableEntity;

import java.util.List;
import java.util.Map;

/**
 * 持久化接口
 *
 * @author daowan.hu
 * @param <Entity>
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

    List<Entity> findByName(Map<String, Object> map);
}
