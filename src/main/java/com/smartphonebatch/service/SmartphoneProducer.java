package com.smartphonebatch.service;

import com.smartphonebatch.model.Smartphone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;


@Service
public class SmartphoneProducer {

    private static final Logger logger = LoggerFactory.getLogger(SmartphoneProducer.class);
    private static final String TOPIC = "smartphones-topic";

    private final KafkaTemplate<String, Smartphone> kafkaTemplate;

    public SmartphoneProducer(KafkaTemplate<String, Smartphone> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    /*
    public void sendSmartphone(Smartphone smartphone) {
        logger.info("📤 Envoi du smartphone au topic Kafka : {}", smartphone);
        System.out.println("⚙️ Tentative d'envoi Kafka : " + smartphone.getMarque());
        kafkaTemplate.send(TOPIC, smartphone);
    }
    */
    public void sendSmartphone(Smartphone smartphone) {
        logger.info("📤 Envoi du smartphone au topic Kafka : {}", smartphone);
        
        CompletableFuture<SendResult<String, Smartphone>> future = kafkaTemplate.send(TOPIC, smartphone);

        future.thenAccept(result -> {
            logger.info("✅ Kafka Async OK : {}", result.getRecordMetadata());
        }).exceptionally(ex -> {
            logger.error("❌ Kafka Async KO pour {}", smartphone.toString(), ex);
            return null;
        });

        kafkaTemplate.flush();
    }
}