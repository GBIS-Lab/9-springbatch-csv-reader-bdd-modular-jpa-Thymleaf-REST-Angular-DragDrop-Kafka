package com.smartphonebatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
@EnableKafka
@EntityScan(basePackages = "com.smartphonebatch.model")
@EnableJpaRepositories(basePackages = "com.smartphonebatch.repository")
public class SmartphoneBatchApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SmartphoneBatchApplication.class, args);

        /*
        //LANCEMENT AU DEMARRAGE DU BATCH

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);

        Job job = context.getBean("smartphoneJob", Job.class);

        try {
            String inputFilePath = "C:/Users/<user>/IdeaProjects/smartphonebatch9 - CSV-BDD-controller-Thymeleaf JPA Angular DragDrop Kafka/src/main/resources/smartphones.csv";

            jobLauncher.run(job, new JobParametersBuilder()
                    .addString("inputFile", inputFilePath)
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}