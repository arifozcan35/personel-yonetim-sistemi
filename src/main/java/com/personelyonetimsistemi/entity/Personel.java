package com.personelyonetimsistemi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Entity
@Table(name = "personel", schema = "dbpersonel")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Personel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personel_id")
    private Long personelId;

    private String isim;

    private String email;

    private Boolean yonetici;

    private Double maas;

    public Personel(Boolean yonetici){
        if(yonetici == true){
            this.maas = 40000.0;
        }
        else{
            this.maas = 30000.0;
        }
    }

    public Personel(Double maas){
        if(maas.equals(30000.0) || maas.equals(40000.0)){
            if(maas.equals(40000.0)){
                this.yonetici = true;
                this.maas = 40000.0;
            }else{
                this.yonetici = false;
                this.maas = 30000.0;
            }
        }else{
            if (Math.abs(maas - 30000.0) < Math.abs(maas - 40000.0)) {
                this.yonetici = false;
                this.maas = 30000.0;
            } else {
                this.yonetici = true;
                this.maas = 40000.0;
            }
            new ResponseEntity<>("Maaş sadece 30000.0 veya 40000.0 olabilir! Girdiğiniz değer " +
                    "bu iki değerden hangisine daha yakınsa o değere (" + this.maas + ") atandı!" , HttpStatus.BAD_REQUEST);
        }
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_birim_id")
    private Birim birim;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_mesai_id")
    private Mesai mesai;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_gise_id")
    private Gise gise;

}
