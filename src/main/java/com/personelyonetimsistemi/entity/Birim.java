package com.personelyonetimsistemi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "birim", schema = "dbpersonel")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Birim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "birim_id")
    private Long birimId;

    private String birimAd;

    @OneToMany(mappedBy = "birim")
    @JsonIgnore
    private List<Personel> personeller;


}
