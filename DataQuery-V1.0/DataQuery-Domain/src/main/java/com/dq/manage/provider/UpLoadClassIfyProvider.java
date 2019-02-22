package com.dq.manage.provider;

import com.dq.manage.entity.Node;
import com.dq.manage.entity.UploadClassify;
import com.iov.common.framework.dubbointerface.BaseDubboInterface;
import com.iov.common.framework.exception.DubboProviderException;

import java.util.List;

/**
 * @author wei.wang5  ${date}
 */
public interface UpLoadClassIfyProvider extends BaseDubboInterface<UploadClassify> {


    /**
     * 根据父节点获取机构树
     *
     * @param node
     * @return
     * @throws DubboProviderException
     */
    public List<Node> getNodes(Node node) throws DubboProviderException;


    /**
     * 验证分类下是否有树
     * @param id
     * @return
     * @throws DubboProviderException
     */
    public boolean check(String id)throws DubboProviderException;
}
