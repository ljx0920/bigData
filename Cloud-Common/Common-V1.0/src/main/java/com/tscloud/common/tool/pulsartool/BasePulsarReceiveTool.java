package com.tscloud.common.tool.pulsartool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pulsar.client.api.*;

/**
 * Pulsar消息接收工具类
 *
 * @author xiuyou.xu
 * @date 2018/7/31 11:27
 */
public abstract class BasePulsarReceiveTool {
    private static final Logger log = LogManager.getLogger(BasePulsarReceiveTool.class);

    private static final String BROKER_SCHEMA = "pulsar://";

    protected Consumer<String> consumer = null;

    public void receiveMsgByStr(String url, String topic, String subscription) {
        try {
            consumer = this.getConsumer(url, topic, subscription);
            while (true) {
                Message<String> message = consumer.receive();
                this.action(topic, message.getKey(), message.getValue());

                consumer.acknowledge(message);
            }
        } catch (Exception e) {
            log.error("url:" + url + "topic:" + topic + "", e);
        }
    }

    /**
     * 得到一个PulsarConsumer
     *
     * @param url
     * @param topic
     * @param subscription
     * @return
     */
    protected Consumer<String> getConsumer(String url, String topic, String subscription) {
        this.initConsumer(url, topic, subscription);
        return consumer;
    }

    /**
     * 关闭当前Consumer
     */
    public void closeConsumer() {
        if (this.consumer != null) {
            try {
                this.consumer.close();
            } catch (PulsarClientException e) {
                log.error("failed to close consumer: ", e);
            } finally {
                this.consumer = null;
            }
        }
    }

    /**
     * 初始化一个Consume
     *
     * @param url          如：pulsar://10.10.1.82:6650
     * @param topicName
     * @param subscription
     */
    private void initConsumer(String url, String topicName, String subscription) {
        String[] urls = url.split(",");
        String u = urls[0];
        try {
            if (!u.toLowerCase().startsWith(BROKER_SCHEMA)) {
                u = BROKER_SCHEMA + u;
            }
            PulsarClient client = PulsarClient.builder().serviceUrl(u).build();

            consumer = client.newConsumer(Schema.STRING)
                    .topic(topicName)
                    .subscriptionName(subscription)
                    .subscriptionType(SubscriptionType.Shared)
                    .subscribe();
        } catch (Exception e) {
            log.error("URL:" + url + "Pulsar Consumer 初始化失败!", e);
        }
    }

    /**
     * 接受到新的消息之后执行的方法
     *
     * @return
     */
    public abstract void action(String topic, String key, String msg);
}
