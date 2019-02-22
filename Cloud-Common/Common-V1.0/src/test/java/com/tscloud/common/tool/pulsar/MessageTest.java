package com.tscloud.common.tool.pulsar;

import com.tscloud.common.tool.pulsartool.BasePulsarReceiveTool;
import com.tscloud.common.tool.pulsartool.PulsarSendTool;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author xiuyou.xu
 * @date 2018/7/30 16:00
 */
public class MessageTest {
    /**
     * java org.apache.pulsar.test.MessageTest
     *
     * @param args
     * @throws PulsarClientException
     */
    public static void main(String[] args) throws PulsarClientException, JsonProcessingException {
//        testProducer();
        testProduce();

//        testConsumer();
//        testConsume();
    }

    private static void testConsumer() throws PulsarClientException {
        PulsarClient client = PulsarClient.builder().serviceUrl("pulsar://10.10.1.82:6650").build();

        Consumer consumer = client.newConsumer(Schema.STRING)
                .topic("test_my")
                .subscriptionName("my-sub")
                .subscriptionType(SubscriptionType.Shared)
                .subscribe();

        do {
            Message<String> message = consumer.receive();

            System.out.printf("message received: %s", message.getValue());

            consumer.acknowledge(message);
        } while (true);
    }

    private static void testConsume() {
        BasePulsarReceiveTool tool = new BasePulsarReceiveTool() {
            @Override
            public void action(String topic, String key, String msg) {
                System.out.println("key = "+key+", value = "+msg);
            }
        };
        tool.receiveMsgByStr("10.10.1.82:6650,10.10.1.83:6650", "test_my", "my-sub");
    }

    private static void testProducer() throws PulsarClientException, JsonProcessingException {
        PulsarClient client = PulsarClient.builder().serviceUrl("pulsar://10.10.1.82:6650").build();

        Schema schema = Schema.STRING;
        Producer<String> producer = client.newProducer(schema)
                .topic("test_my")
                .create();

        producer.newMessage().key("key")
                .value("hello pulsar")
                .send();

        producer.close();
        client.close();
    }

    private static void testProduce() {
        for(int i=0;i<100;i++){
            String msg = "";
            PulsarSendTool.getInstance().sendMsgByStr("10.10.1.82:6650,10.10.1.83:6650", "test_my", "hudw", "hello pulsar"+i);
        }

    }
}
