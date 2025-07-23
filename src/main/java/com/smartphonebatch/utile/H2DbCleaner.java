package com.smartphonebatch.utile;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class H2DbCleaner {

    private static final String DB_DIR = "data"; // ou le chemin absolu complet si besoin
    private static final String[] FILES_TO_DELETE = {
            "testdb.mv.db",
            "testdb.trace.db"
    };

    @PostConstruct
    public void cleanDbFiles() {
        for (String fileName : FILES_TO_DELETE) {
            File file = new File(DB_DIR, fileName);
            if (file.exists()) {
                boolean deleted = file.delete();
                System.out.println("🧹 Suppression " + file.getPath() + " : " + (deleted ? "OK" : "ÉCHEC"));
            }
        }
    }
}