package com.personelyonetimsistemi.service;

import com.personelyonetimsistemi.entity.Mesai;
import com.personelyonetimsistemi.entity.Personel;
import com.personelyonetimsistemi.repository.MesaiRepository;
import com.personelyonetimsistemi.repository.PersonelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@Service
public class MesaiService {
    @Autowired
    private MesaiRepository mesaiRepository;
    @Autowired
    private PersonelRepository personelRepository;

    private static final LocalTime MESAI_BASLANGIC = LocalTime.of(9, 0);
    private static final LocalTime MESAI_BITIS = LocalTime.of(18, 0);
    private static final Duration MAX_MESAI_EKSIKLIK = Duration.ofMinutes(15);
    private static final double CEZA_TUTARI = 200.0;

    public Mesai getOneMesaiofPersonel(Long personelId){
        Personel personel = new Personel();
        personel.setPersonelId(personelId);
        return mesaiRepository.findById(personelId).orElse(null);
    }

    public void mesaiHesapla(Long personelId) {
        Personel personel = personelRepository.findById(personelId).orElse(null);
        Mesai mesai = personel.getMesai();

        Double maas = personel.getMaas();

        LocalTime girisSaati = mesai.getGirisSaati();
        LocalTime cikisSaati = mesai.getCikisSaati();

        Duration mesaiSure = calculateMesaiSure(girisSaati, cikisSaati);

        boolean valid = isMesaiValid(girisSaati, cikisSaati);
        personel.getMesai().setMesaiGecerliMi(valid);

            if (personel.getYonetici() != null && personel.getYonetici() == true) {
                personel.setMaas(maas);
            } else {
                // personel.setMaas(maas);
                if (!valid) {
                    double ceza = calculateCeza(mesaiSure);
                    personel.setMaas(((personel.getMaas()) - (ceza)));
                }
            }

        personelRepository.save(personel);
        // return personelRepository.save(personel);
    }


    private Duration calculateMesaiSure(LocalTime girisSaati, LocalTime cikisSaati) {
        return Duration.between(girisSaati, cikisSaati);
    }

    private boolean isMesaiValid(LocalTime girisSaati, LocalTime cikisSaati) {
        Duration mesaiSure = calculateMesaiSure(girisSaati, cikisSaati);
        Duration mesaiLimit = Duration.between(MESAI_BASLANGIC, MESAI_BITIS).minus(MAX_MESAI_EKSIKLIK);
        return mesaiSure.compareTo(mesaiLimit) >= 0;
    }

    private double calculateCeza(Duration mesaiSure) {
        if (mesaiSure.compareTo(Duration.between(MESAI_BASLANGIC, MESAI_BITIS)) < 0) {
            return CEZA_TUTARI;
        }
        return 0.0;
    }
}
