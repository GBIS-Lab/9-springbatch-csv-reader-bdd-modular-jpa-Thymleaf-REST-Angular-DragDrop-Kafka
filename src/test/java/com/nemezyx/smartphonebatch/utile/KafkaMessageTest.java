package com.nemezyx.smartphonebatch.utile;

/*
import jdk.javadoc.internal.doclets.toolkit.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.Map;

import static org.testng.AssertJUnit.assertFalse;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
class KafkaMessageTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker; // facultatif si on vise Redpanda réel

    @Test
    void shouldConsumeMessagesFromKafka() {
        // Setup du Consumer
        Utils KafkaTestUtils;
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, String> consumer = cf.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "smartphones");

        // Récupération des messages
        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(10));

        assertFalse(records.isEmpty(), "Aucun message reçu dans le topic");

        records.forEach(record -> {
            System.out.println("MESSAGE: " + record.value());
            assertNotNull(record.value());
            // Tu peux parser le JSON ici si besoin
        });

        consumer.close();
    }
}*/