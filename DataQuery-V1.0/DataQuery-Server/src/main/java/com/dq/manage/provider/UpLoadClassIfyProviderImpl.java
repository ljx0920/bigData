package com.dq.manage.provider;

import com.dq.manage.entity.Node;
import com.dq.manage.entity.UploadClassify;
import com.dq.manage.entity.UploadFileItem;
import com.dq.manage.mapper.UpLoadClassIfylMapper;
import com.dq.manage.mapper.UploadFileItemMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iov.common.framework.dubbointerface.impl.BaseDubboInterfaceImpl;
import com.iov.common.framework.exception.DubboProviderException;
import com.iov.common.framework.mapper.BaseInterfaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;

/**
 * @author wei.wang5  ${date}
 */
public class UpLoadClassIfyProviderImpl extends BaseDubboInterfaceImpl<UploadClassify> implements UpLoadClassIfyProvider   {


    @Autowired
    @Qualifier("upLoadClassIfylMapper")
    public UpLoadClassIfylMapper upLoadClassIfylMapper;
    //@Override
    public String findByDateName(String name) throws DubboProviderException{
        return upLoadClassIfylMapper.findByDateName(name);
    }

    @Autowired
    @Qualifier("uploadFileItemMapper")
    private UploadFileItemMapper uploadFileItemMapper;

    @Override
    public BaseInterfaceMapper<UploadClassify> getBaseInterfaceMapper() {
        return upLoadClassIfylMapper;
    }

    @Override
    public List<Node> getNodes(Node node) throws DubboProviderException {
        return getNodeByParent(node);
    }

    @Override
    public boolean check(String id) throws DubboProviderException {
        boolean flag = true;
        List<UploadClassify> list = upLoadClassIfylMapper.findByUpLoadClassIfyId(id);
        List<UploadFileItem> fileItemList = uploadFileItemMapper.findByClassIfyIdGetFileItem(id);
        if (list.size() > 0 || fileItemList.size()> 0) {
            flag = false;
        }
        return flag;
    }

    private List<Node> getNodeByParent(Node node) {
        List<Node> deptTree = Lists.newArrayList();
        Map<String, Object> params = Maps.newHashMap();
        if (null == node) {
            params.put("parentId", "0");
        } else {
            params.put("parentId", node.getId());
        }
        List<UploadClassify> uploadClassifyList = upLoadClassIfylMapper.findByMap(params);
        for (UploadClassify uploadItem : uploadClassifyList) {
            Node deptNode = Node.newInstance();
            deptNode.setId(uploadItem.getId().toString());
            deptNode.setName(uploadItem.getName());
            deptNode.setData(uploadItem);
            //deptNode.setLevel(uploadItem.getLevel());
            deptNode.setChildren(getNodeByParent(deptNode));
            deptTree.add(deptNode);
        }
        return deptTree;
    }
}
