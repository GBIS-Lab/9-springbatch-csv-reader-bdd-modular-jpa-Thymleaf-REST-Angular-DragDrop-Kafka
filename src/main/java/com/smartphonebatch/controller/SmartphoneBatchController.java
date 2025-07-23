package com.smartphonebatch.controller;

import com.smartphonebatch.config.BatchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartphoneBatchController {

    @Autowired
    private BatchConfig batchConfig;

    /*
    @PostMapping("/api/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // Sauvegarder temporairement le fichier
        String filePath = "path/to/temp/storage/" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        // Passer le chemin au lecteur pour démarrer le batch
        //!!!!!!!!!!!!!!!!!!!!!!!!batchConfig.setFilePathForReader(filePath);

        // Lancer le batch si nécessaire (ou laisser un autre mécanisme pour démarrer le batch)
        // Exemple : jobLauncher.run(job, parameters);

        return "Fichier reçu et traitement démarré!";
    }*/
}