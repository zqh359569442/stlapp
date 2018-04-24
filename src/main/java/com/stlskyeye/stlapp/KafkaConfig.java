package com.stlskyeye.stlapp;

import java.util.*;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;


public class KafkaConfig {

    private final static String URL = "192.168.197.129:3181";
    private final static String NAME = "test_topic";

    // 创建主题
    private static void createTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 创建一个单分区单副本名为t1的topic
        AdminUtils.createTopic(zkUtils, NAME, 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
        zkUtils.close();
        System.out.println("创建成功!");
    }
    // 删除主题(未彻底删除)
    private static void deleteTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 删除topic 't1'
        AdminUtils.deleteTopic(zkUtils, NAME);
        zkUtils.close();
        System.out.println("删除成功!");
    }

    // 修改主题
    private static void editTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), NAME);
        // 增加topic级别属性
        props.put("min.cleanable.dirty.ratio", "0.3");
        // 删除topic级别属性
        props.remove("max.message.bytes");
        // 修改topic 'test'的属性
        AdminUtils.changeTopicConfig(zkUtils, NAME, props);
        zkUtils.close();
    }

    // 主题读取
    private static void queryTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 获取topic 'test'的topic属性属性
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), NAME);
        // 查询topic-level属性
        Iterator it = props.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        zkUtils.close();
    }

    /**
     * @Description: 生产者
     */
    private static void producer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.197.129:9092");
        props.put("acks", "-1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<>("test_topic", Integer.toString(i), Integer.toString(i)));
        //producer.flush();
        producer.close();
    }




    /**
     * @Description: 消费者
     */
    private static void customer() {
        try {
            Properties properties = new Properties();
            properties.put("zookeeper.connect", URL);
            properties.put("auto.commit.enable", "true");
            properties.put("auto.commit.interval.ms", "60000");
            properties.put("group.id", "test_topic");

            ConsumerConfig consumerConfig = new ConsumerConfig(properties);

            ConsumerConnector javaConsumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

            // topic的过滤器
            Whitelist whitelist = new Whitelist("test_topic");
            List<KafkaStream<byte[], byte[]>> partitions = javaConsumerConnector
                    .createMessageStreamsByFilter(whitelist);

            if (partitions == null) {
                System.out.println("empty!");
                TimeUnit.SECONDS.sleep(1);
            }

            System.out.println("partitions:"+partitions.size());

            // 消费消息
            for (KafkaStream<byte[], byte[]> partition : partitions) {

                ConsumerIterator<byte[], byte[]> iterator = partition.iterator();
                while (iterator.hasNext()) {
                    MessageAndMetadata<byte[], byte[]> next = iterator.next();
                    System.out.println("partiton:" + next.partition());
                    System.out.println("offset:" + next.offset());
                    System.out.println("接收到message:" + new String(next.message(), "utf-8"));
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


   /* public static void getNumCustomer(){
        Properties properties = new Properties();
        properties.put("zookeeper.connect", URL);
        properties.put("auto.commit.enable", "true");
        properties.put("auto.commit.interval.ms", "60000");
        properties.put("group.id", "test_topic");
        KafkaConsumer consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(properties.getProperty("group.id")));


        //每5s取一次数据
        ConsumerRecords<String, String> records = consumer.poll(5000);

        //遍历所有的partition
        for(TopicPartition tp : records.partitions()) {
            //每次处理多少条数据
            int maxpoll = 0;
            //当前消息的offset
            long currentOffset = 0;

            //获取partition中的消息
            List<ConsumerRecord<String, String>> partitionRecords = records.records(tp);
            for (ConsumerRecord<String, String> record : partitionRecords) {
                try {
                    JSONObject json = JSONObject.fromObject(record.value());
                    Integer operatetype = json.getInt("operatetype");

                    if (operatetype != null) {
                        ecarGrouponService.createGrouponDoc(json.toString());
                    } else {
                        ecarGrouponService.updateGrouponDoc(json.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                currentOffset = record.offset();
                maxpoll++;

                if (maxpoll > 20) {
                    break;
                }
            }

            //指定消费到的位置
            long lastoffset = currentOffset + 1;
            //指定下次poll的位置
            consumer.seek(tp, lastoffset);
            consumer.commitSync(Collections.singletonMap(tp, new OffsetAndMetadata(lastoffset)));
        }*/


    public static void main(String[] args) {
         //createTopic();
//       deleteTopic();
        // editTopic();
        // queryTopic();

        producer();

     // customer();

    }
}
