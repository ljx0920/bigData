package com.tscloud.common.tool.pulsartool;

import com.google.common.collect.Maps;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.common.policies.data.SubscriptionStats;
import org.apache.pulsar.common.policies.data.TopicStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * pulsar管理工具
 *
 * @author xiuyou.xu
 * @date 2018/9/7 10:47
 */
public class PulsarAdminTool {
    private static Logger logger = LoggerFactory.getLogger(PulsarAdminTool.class);
    private static volatile PulsarAdminTool instance;

    private static Map<String, PulsarAdmin> adminMap = Maps.newConcurrentMap();

    private PulsarAdminTool() {
    }

    public static PulsarAdminTool getInstance() {
        if (instance == null) {
            synchronized (PulsarAdminTool.class) {
                if (instance == null) {
                    instance = new PulsarAdminTool();
                }
            }
        }
        return instance;
    }

    /**
     * 关闭指定的admin
     * @param url
     */
    public void close(String url) {
        PulsarAdmin admin = adminMap.get(url);
        if (admin != null) {
            admin.close();
        }
    }

    /**
     * 关闭所有的admin
     */
    public void closeAll() {
        adminMap.forEach((url, admin) -> {
            admin.close();
        });
    }

    /**
     * 查询指定topic中指定subscription的堆积消息数
     *
     * @param url
     * @param topic
     * @param subscription
     * @return
     * @throws Exception
     */
    public long getMsgBacklog(String url, String topic, String subscription) throws Exception {
        PulsarAdmin admin = adminMap.get(url);
        if (admin == null) {
            admin = PulsarAdmin.builder()
                    .serviceHttpUrl(url)
                    .allowTlsInsecureConnection(true)
                    .enableTlsHostnameVerification(false)
                    .build();
            adminMap.put(url, admin);
        }

        Topics topics = admin.topics();
        TopicStats topicStats = topics.getStats(topic);
        if (topicStats != null) {
            SubscriptionStats subStats = topicStats.subscriptions.get(subscription);
            if (subStats != null) {
                return subStats.msgBacklog;
            }
            throw new Exception("subscription not found: " + subscription);
        }
        throw new Exception("topic not found: " + topic);
    }

}
