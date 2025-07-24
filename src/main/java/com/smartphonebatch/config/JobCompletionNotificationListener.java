package com.smartphonebatch.config;

import com.smartphonebatch.model.Smartphone;
import com.smartphonebatch.repository.SmartphoneRepository;
import com.smartphonebatch.service.SmartphoneProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    public static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final SmartphoneRepository smartphoneRepository;
    private final SmartphoneProducer smartphoneProducer;

    public JobCompletionNotificationListener(SmartphoneRepository smartphoneRepository,
                                             SmartphoneProducer smartphoneProducer) {
        this.smartphoneRepository = smartphoneRepository;
        this.smartphoneProducer = smartphoneProducer;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("🎯 JOB TERMINÉ - Publication Kafka en cours...");

            List<Smartphone> smartphones = smartphoneRepository.findAll();
            smartphones.forEach(smartphoneProducer::sendSmartphone);

            log.info("📤 {} smartphones publiés dans Kafka", smartphones.size());
        }
    }
}
