package com.tscloud.common.tool.pulsartool;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import java.util.Map;

/**
 * Pulsar消息发送工具类
 *
 * @author xiuyou.xu
 * @date 2018/7/31 11:27
 */
public class PulsarSendTool {

    private static final Logger log = LogManager.getLogger(PulsarSendTool.class);

    private volatile static PulsarSendTool instance = null;

    private volatile static PulsarClient client = null;
    private static Map<String, Producer> producerMap = Maps.newConcurrentMap();

    private static final String BROKER_SCHEMA = "pulsar://";

    public PulsarSendTool() {
    }

    public static PulsarSendTool getInstance() {
         instance = new PulsarSendTool();
        return instance;
    }

    /**
     * 发送一个消息
     *
     * @param url
     * @param topic
     * @param key
     * @param msg
     * @return
     */
    public boolean sendMsgByStr(String url, String topic, String key, String msg) {
        boolean flag = true;
        try {
            Producer<String> producer = this.getProducer(url, topic);
            if (producer == null) {
                this.getProducer(url, topic);
            }
            assert producer != null;
            producer.newMessage().key(key)
                    .value(msg)
                    .send();
            log.debug("Send Pulsar Topic:[" + topic + "]  Msg:[" + msg + "]");
        } catch (Exception e) {
            flag = false;
            log.error("Topic:" + topic + ", key:" + key + " send  failure !", e);
        }
        return flag;
    }

    /**
     * 得到一个Producer
     *
     * @param url       如：pulsar://10.10.1.82:6650
     * @param topicName
     */
    public Producer<String> getProducer(String url, String topicName) throws Exception {
        String[] urls = url.split(",");
        String u = urls[0];
        try {
            if (!u.toLowerCase().startsWith(BROKER_SCHEMA)) {
                u = BROKER_SCHEMA + u;
            }
            if (client == null) {
                client = PulsarClient.builder().serviceUrl(u).build();
            }

            if (client != null) {
                Schema schema = Schema.STRING;
                Producer<String> producer = producerMap.get(topicName);
                if (producer == null) {
                    producer = client.newProducer(schema)
                            .topic(topicName)
                            .create();
                    producerMap.put(topicName, producer);
                    log.info("PulsarSendTool: producer init - Pulsar Producer 初始化完成! url = {}", u);
                }
                return producer;
            } else {
                throw new Exception("failed to create producer, client is null");
            }
        } catch (Exception e) {
            log.error("URL:" + url + "Pulsar Producer 初始化失败！", e);
            throw e;
        }
    }

    /**
     * 关闭当前Producer
     */
    public void closeProducer(Producer producer) {
        if (producer != null) {
            try {
                producer.close();
            } catch (PulsarClientException e) {
                log.error("failed to close producer: ", e);
            } finally {
                producer = null;
            }
        }
    }
}
