package com.smartphonebatch.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "smartphones")
@Data
public class Smartphone implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;
    private String modele;
    //private String capacite;
    private String os;

    @Column(name = "annee_sortie")
    private Integer annee;

    private double tailleEcran;
    private double prix;

}
