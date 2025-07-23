package com.smartphonebatch.utile;

import com.smartphonebatch.model.Smartphone;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaReplayReader {

    public static void main(String[] args) {
        String topic = "smartphones-topic";
        String bootstrapServers = "localhost:9092";
        String groupId = "replay-reader-group"; // Nouveau group ID pour éviter l’offset existant

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Smartphone.class.getName());

        try (KafkaConsumer<String, Smartphone> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(topic));

            System.out.println("📦 Lecture des messages Kafka depuis le début du topic...");

            while (true) {
                ConsumerRecords<String, Smartphone> records = consumer.poll(Duration.ofSeconds(2));
                if (records.isEmpty()) {
                    System.out.println("✅ Fin des messages Kafka.");
                    break;
                }

                for (ConsumerRecord<String, Smartphone> record : records) {
                    System.out.printf("🔁 Reçu depuis partition=%d, offset=%d, key=%s, value=%s%n",
                            record.partition(), record.offset(), record.key(), record.value());
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur pendant la relecture des messages Kafka : " + e.getMessage());
            e.printStackTrace();
        }
    }
}