package com.smartphonebatch.writer;

import com.smartphonebatch.model.Smartphone;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class SmartphoneItemWriter extends JdbcBatchItemWriter<Smartphone> {

    public SmartphoneItemWriter(DataSource dataSource) {
        this.setDataSource(dataSource);
        this.setSql("""
            INSERT INTO smartphones (marque, modele, os, annee_sortie, taille_ecran, prix)
            VALUES (:marque, :modele, :os, :annee, :tailleEcran, :prix)
        """);
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        this.afterPropertiesSet();
    }

}