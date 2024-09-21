package com.personelyonetimsistemi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "mesai", schema = "dbpersonel")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Mesai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mesai_id")
    private Long mesaiId;

    // private LocalDate tarih;

    private LocalTime girisSaati;

    private LocalTime cikisSaati;

    private Boolean mesaiGecerliMi;


    @OneToOne(mappedBy = "mesai")
    @JsonIgnore
    private Personel personel;

}
