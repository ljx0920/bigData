package com.dq.manage.provider;

import com.dq.manage.entity.User;
import com.dq.manage.mapper.UserMapper;
import com.google.common.collect.Maps;
import com.iov.common.framework.dubbointerface.impl.BaseDubboInterfaceImpl;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.mapper.BaseInterfaceMapper;
import com.iov.common.framework.rest.JsonViewObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;

public class UserLoginProviderImpl extends BaseDubboInterfaceImpl<User> implements UserLoginProvider {

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    public String findByDateName(String name) throws DubboProviderException{
        return "UserLoginProviderImpl中的findByDateName";
    }

    @Override
    public BaseInterfaceMapper<User> getBaseInterfaceMapper() {
        return userMapper;
    }

    private static Logger log = LogManager.getLogger(UserLoginProviderImpl.class);

    @Override
    public JsonViewObject userLogin(Map<String, Object> map) throws DubboProviderException {
        String userName = map.get("username").toString();
        String password = map.get("password").toString();
        //1.解析登录名
        if (userName == null || "".equals(userName)) {
            log.warn("username null.");
            return jsonView.fail("您没有输入用户名");
        }
        if (password == null || "".equals(password)) {
            log.warn("pwd null.");
            return jsonView.fail("您没有输入密码");
        }
        //2.用户登录验证
        Map<String, Object> userInfo = Maps.newHashMap();
        userInfo.put("user",userName);
        userInfo.put("pwd",password);
        List<User> userList = userMapper.findByMap(userInfo);
        if(userList == null ||userList.size() == 0){
            log.warn("user login warn , cause : user or pwd wrong.");
            return jsonView.fail("登录失败，用户名或者密码错误");
        }else {
            Map<String, Object> userInfoMap = Maps.newHashMap();
            Map<String, Object> userNameData = Maps.newHashMap();
            userNameData.put("username",userList.get(0).getUser());
            userNameData.put("name",userList.get(0).getName());
            userInfoMap.put("userInfo",userNameData);
            return jsonView.success(userInfoMap,"登录成功");
        }
    }
}
