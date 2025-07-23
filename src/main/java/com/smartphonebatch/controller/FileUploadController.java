package com.smartphonebatch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class FileUploadController {

    private static final String UPLOAD_DIR = "uploads";

    //@Autowired
    //private BatchLauncherService batchLauncherService;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job smartphoneJob;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        try {

            System.out.println("📄 Taille du fichier reçu : " + file.getSize() + " bytes");

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Le fichier est vide.");
            }

            if (!file.getOriginalFilename().endsWith(".csv")) {
                return ResponseEntity.badRequest().body("Format de fichier non supporté.");
            }

            // Créer le répertoire uploads si besoin
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
            Files.createDirectories(uploadPath);

            // Nettoyer le nom du fichier
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String savedFileName = "smartphones-" + System.nanoTime() + ".csv";
            Path filePath = uploadPath.resolve(savedFileName);

            //Path tempFile = Files.createTempFile("smartphones-", ".csv");
            //file.transferTo(tempFile.toFile());
            //batchLauncherService.launchJob(tempFile.toAbsolutePath().toString());

            /*
            Path filePath = Files.createTempFile(uploadDir.toPath(), "smartphones-", ".csv");
            // Sauvegarder le fichier téléchargé dans ce répertoire
            file.transferTo(filePath.toFile());
            System.out.println("📂 Fichier téléchargé dans : " + filePath);
            // Lancer le job avec le chemin du fichier
            batchLauncherService.launchJob(filePath.toAbsolutePath().toString());
            */

            // Sauvegarder le fichier
            file.transferTo(filePath.toFile());

            System.out.println("✅ Fichier sauvegardé à : " + filePath);

            // Lancer le batch
            jobLauncher.run(smartphoneJob, new JobParametersBuilder()
                    .addString("inputFile", filePath.toString())
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters());

            return ResponseEntity.ok("✅ Fichier uploadé et batch lancé !");

        } catch (Exception e) {
            e.printStackTrace(); // Afficher l'exception dans les logs
            return ResponseEntity.status(500).body("Erreur lors du traitement du fichier.");
        }
    }
}