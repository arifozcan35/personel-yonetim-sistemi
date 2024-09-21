package com.personelyonetimsistemi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "gise", schema = "dbpersonel")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Gise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gise_id")
    private Long giseId;

    private String giseAd;

    //private Boolean gecisIzni;


    @OneToMany(mappedBy = "gise")
    @JsonIgnore
    private List<Personel> personeller;
}
