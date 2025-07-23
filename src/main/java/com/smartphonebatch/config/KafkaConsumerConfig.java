package com.smartphonebatch.config;

import com.smartphonebatch.model.Smartphone;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Bean
    public ConsumerFactory<String, Smartphone> consumerFactory() {
        /*JsonDeserializer<Smartphone> deserializer = new JsonDeserializer<>(Smartphone.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(true);
        deserializer.addTrustedPackages("com.nemezyx.smartphonebatch.model");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "smartphone-consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer  );
        */

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "smartphone-consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // ou mieux : "com.nemezyx.smartphonebatch.model"
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        //props.put(ConsumerConfig.LISTENER_MISSING_TOPICS_FATAL, false);

        //return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(Smartphone.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Smartphone> smartphoneKafkaListenerContainerFactory() {
        log.info("📥 [KafkaConsumerConfig] ConcurrentKafkaListenerContainerFactory entrée : ");
        ConcurrentKafkaListenerContainerFactory<String, Smartphone> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}