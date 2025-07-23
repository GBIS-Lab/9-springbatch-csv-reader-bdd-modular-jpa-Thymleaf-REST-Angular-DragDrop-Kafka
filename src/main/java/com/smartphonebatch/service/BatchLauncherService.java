package com.smartphonebatch.service;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BatchLauncherService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importSmartphonesJob;

    @Autowired
    private SmartphoneProducer smartphoneProducer;

    /*public void publierSmartphonesKafka() {
        ParseTreePattern smartphoneRepository = null;
        List<Smartphone> smartphones = smartphoneRepository.findAll();
        smartphones.forEach(smartphoneProducer::sendSmartphone);
    }*/

    public void launchJob(String filePath) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("inputFile", filePath)
                .addDate("timestamp", new Date())
                .toJobParameters();

        jobLauncher.run(importSmartphonesJob, params);

        System.out.println("🚀 Lancement du batch avec inputFile = " + filePath);
    }
}