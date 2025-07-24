package com.smartphonebatch.config;

import com.smartphonebatch.model.Smartphone;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.beans.factory.ObjectProvider;

import com.smartphonebatch.writer.SmartphoneItemWriter;
//import com.smartphonebatch.reader.SmartphoneItemReader;
import com.smartphonebatch.processor.SmartphoneItemProcessor;


import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    //private final SmartphoneItemReader reader;
    private final SmartphoneItemProcessor processor;
    private final SmartphoneItemWriter writer;

    @Bean
    @StepScope
    public FlatFileItemReader<Smartphone> reader(@Value("#{jobParameters['inputFile']}") String inputFile) {
        if (inputFile == null) {
            throw new IllegalArgumentException("Le paramètre 'inputFile' est requis pour le job");
        }


        System.out.println("➡️ Initialisation du FlatFileItemReader");
        System.out.println("📁 inputFile reçu : " + inputFile);

        FlatFileItemReader<Smartphone> reader = new FlatFileItemReader<>();
        FileSystemResource resource = new FileSystemResource(inputFile);

        System.out.println("📄 Fichier existe ? " + resource.exists());
        System.out.println("📄 Chemin absolu : " + resource.getPath());

        reader.setResource(resource);

        reader.setLinesToSkip(1); // sauter l’en-tête CSV
        reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
        reader.setLineMapper(lineMapper());
        return reader;
    }

    private LineMapper<Smartphone> lineMapper() {
        DefaultLineMapper<Smartphone> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(";");
        tokenizer.setNames("marque", "modele", "os", "annee", "tailleEcran", "prix");

        BeanWrapperFieldSetMapper<Smartphone> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Smartphone.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }


    public BatchConfig(SmartphoneItemProcessor processor,
                       SmartphoneItemWriter writer) {
        //this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @PostConstruct
    public void debug() {
        System.out.println("== Beans init ==");
        //System.out.println("reader: " + reader);
        System.out.println("processor: " + processor);
        System.out.println("writer: " + writer);
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager,
                     ObjectProvider<FlatFileItemReader<Smartphone>> readerProvider) throws IOException
        {
        return new StepBuilder("step1", jobRepository)
                .<Smartphone, Smartphone>chunk(10, transactionManager)
                .reader(readerProvider.getObject())
                .processor(processor)
                //.writer(items -> {
                //	System.out.println("== Résultat du traitement ==");
                //	items.forEach(System.out::println);
                //})
                .writer(writer)
                .build();
    }

    @Bean
    public Job smartphoneJob(JobRepository jobRepository,
                   Step step) {
        return new JobBuilder("smartphoneJob", jobRepository)
                .start(step)
                .build();
    }
}
