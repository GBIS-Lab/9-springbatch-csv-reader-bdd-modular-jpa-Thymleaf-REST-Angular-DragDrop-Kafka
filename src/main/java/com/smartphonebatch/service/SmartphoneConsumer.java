package com.smartphonebatch.service;

import com.smartphonebatch.model.Smartphone;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Component
public class SmartphoneConsumer {

    private static final Logger log = LoggerFactory.getLogger(SmartphoneConsumer.class);
    private final List<Smartphone> consumedSmartphones = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        log.info("✅ SmartphoneConsumer initialisé et en attente de messages Kafka.");
    }

    //@KafkaListener(topics = "smartphones-topic", groupId = "smartphone-reader-test1")
    @KafkaListener(
            topics = "smartphones-topic",
            groupId = "smartphone-consumer-group",
            containerFactory = "smartphoneKafkaListenerContainerFactory"
    )
    public void listen(Smartphone smartphone) {
        log.info("🎧 [LISTENER] Démarrage méthode listen() pour le topic 'smartphones-topic'");

        if (smartphone == null) {
            log.warn("⚠️ [LISTENER] Message reçu est NULL ! Possible mauvaise désérialisation ou aucun message reçu.");
            return;
        }

        try {
            log.info("📥 [CONSUMER] Message reçu depuis Kafka : {}", smartphone);
            consumedSmartphones.add(smartphone);
            log.info("📥 Message consommé : {}", smartphone);
        } catch (Exception e) {
            log.error("❌ Erreur lors du traitement SmartphoneConsumer.listen() du message Kafka : {}", smartphone, e);
        }
    }

    public List<Smartphone> getConsumedSmartphones() {
        try {
            log.info("📊 [CONSUMER] Nombre total de messages consommés : {}", consumedSmartphones.size());
            return consumedSmartphones;
        } catch (Exception e) {
            log.error("❌ Erreur lors du traitement SmartphoneConsumer.getConsumedSmartphones() de la récupération des smartphones consommés", e);
            return List.of(); // Liste vide pour éviter de planter l’appelant
        }
    }
}