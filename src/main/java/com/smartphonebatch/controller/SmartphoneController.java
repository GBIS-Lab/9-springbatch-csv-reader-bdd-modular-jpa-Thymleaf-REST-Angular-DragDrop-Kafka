package com.smartphonebatch.controller;

import com.smartphonebatch.model.Smartphone;
import com.smartphonebatch.repository.SmartphoneRepository;
import com.smartphonebatch.service.SmartphoneConsumer;
//import com.nemezyx.smartphonebatch.service.SmartphoneKafkaReader;
import com.smartphonebatch.service.SmartphoneProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//@Controller1
@RestController
@RequestMapping("/api/smartphones")
@CrossOrigin(origins = "*") // Pour autoriser Angular à appeler l’API
public class SmartphoneController {

    private static final Logger log = LoggerFactory.getLogger(SmartphoneController.class);

    private final SmartphoneRepository smartphoneRepository;
    private final SmartphoneProducer smartphoneProducer;
    private final SmartphoneConsumer smartphoneConsumer;

    public SmartphoneController(SmartphoneRepository smartphoneRepository,
                                SmartphoneProducer smartphoneProducer,
                                SmartphoneConsumer consumer
                                ) {
        this.smartphoneRepository = smartphoneRepository;
        this.smartphoneProducer = smartphoneProducer;
        this.smartphoneConsumer = consumer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishAllSmartphonesToKafka() {
        List<Smartphone> smartphones = smartphoneRepository.findAll();

        if (smartphones.isEmpty()) {
            log.warn("⚠️ Aucun smartphone trouvé en base pour publication Kafka.");
            return ResponseEntity.ok("⚠️ Aucun smartphone à publier.");
        }

        //smartphones.forEach(smartphoneProducer::sendSmartphone);
        smartphones.forEach(s -> {
            log.info("📤 [PUBLISHER] Envoi du smartphone : {}", s);
            smartphoneProducer.sendSmartphone(s);
        });
        return ResponseEntity.ok("📤 " + smartphones.size() + " smartphones publiés dans Kafka.");
    }

    @GetMapping("/smartphonesList")
    public String getSmartphones(Model model) {
        log.info("📄 Affichage de la liste des smartphones dans le template Thymeleaf.");
        model.addAttribute("smartphones", smartphoneRepository.findAll());
        return "smartphonesList"; // correspond au nom du fichier HTML
    }

    @GetMapping
    public List<Smartphone> getAllSmartphones() {
        log.info("📂 Récupération de tous les smartphones depuis la base de données.");
        return smartphoneRepository.findAll();
    }

    // ✅ Méthode pour l'accès par ID :
    @GetMapping("/{id}")
    public ResponseEntity<Smartphone> getSmartphoneById(@PathVariable Long id) {
        log.info("🔍 Récupération du smartphone avec l'ID : {}", id);
        return smartphoneRepository.findById(id)
                .map(s -> {
                    log.info("✅ Smartphone trouvé : {}", s);
                    return ResponseEntity.ok(s);
                })
                .orElseGet(() -> {
                    log.warn("❌ Aucun smartphone trouvé avec l'ID : {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/consumed")
    public ResponseEntity<List<Smartphone>> getConsumedSmartphones() {
        try {
            List<Smartphone> consumed = smartphoneConsumer.getConsumedSmartphones();
            log.info("✅ Envoi de la liste des smartphones consommés : {} élément(s)", consumed.size());
            return ResponseEntity.ok(consumed);
        } catch (Exception e) {
            log.error("❌ Erreur lors du traitement SmartphoneController.getConsumedSmartphones() de la récupération des messages consommés Kafka", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}