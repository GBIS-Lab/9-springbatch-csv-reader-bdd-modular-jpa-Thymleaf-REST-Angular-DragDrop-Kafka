/*
package com.nemezyx.smartphonebatch.service;


import com.nemezyx.smartphonebatch.model.Smartphone;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class SmartphoneKafkaReader {

    private final String TOPIC = "smartphones-topic";

    public List<Smartphone> readFromKafka() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // ou Redpanda:29092
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "rest-reader-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.nemezyx.smartphonebatch.model.Smartphone");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        List<Smartphone> results = new ArrayList<>();

        try (KafkaConsumer<String, Smartphone> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(TOPIC));

            ConsumerRecords<String, Smartphone> records = consumer.poll(Duration.ofSeconds(3));

            for (ConsumerRecord<String, Smartphone> record : records) {
                results.add(record.value());
            }
        }

        return results;
    }
}
*/