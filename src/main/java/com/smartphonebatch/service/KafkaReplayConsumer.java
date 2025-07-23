package com.smartphonebatch.service;

import com.smartphonebatch.model.Smartphone;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class KafkaReplayConsumer {

    private final List<Smartphone> replayedMessages = new CopyOnWriteArrayList<>();

    public List<Smartphone> replayMessages() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "smartphone-replay-group-" + UUID.randomUUID()); // Unique groupId à chaque appel
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", "org.springframework.kafka.support.serializer.JsonDeserializer");
        props.put("spring.json.trusted.packages", "*");
        props.put("spring.json.value.default.type", Smartphone.class.getName());

        try (KafkaConsumer<String, Smartphone> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singleton("smartphones-topic"));
            ConsumerRecords<String, Smartphone> records = consumer.poll(Duration.ofSeconds(5));

            replayedMessages.clear();
            for (ConsumerRecord<String, Smartphone> record : records) {
                replayedMessages.add(record.value());
            }
        }

        return replayedMessages;
    }

    public List<Smartphone> getReplayedMessages() {
        return replayedMessages;
    }
}