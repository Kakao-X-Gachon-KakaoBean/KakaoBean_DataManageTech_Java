package com.kakaobean.dataTech.config;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaAdmin kafkaAdmin;

    public static final String BASIC_TOPIC_NAME = "basic_topic";
    public static final String STREAMS_TOPIC_NAME = "streams_topic";

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";


    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate() {
        return new KafkaTemplate<String,Object>(producerFactory());
    }

    @PostConstruct
    void initKakaTopic(){
        NewTopic newTopic = new NewTopic(BASIC_TOPIC_NAME, 1, (short) 1);
        NewTopic streamsTopic = new NewTopic(STREAMS_TOPIC_NAME, 1, (short) 1);

        kafkaAdmin.createOrModifyTopics(newTopic);
        kafkaAdmin.createOrModifyTopics(streamsTopic);
    }
}
