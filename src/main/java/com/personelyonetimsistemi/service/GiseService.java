package com.personelyonetimsistemi.service;

import com.personelyonetimsistemi.entity.Gise;
import com.personelyonetimsistemi.entity.Personel;
import com.personelyonetimsistemi.repository.GiseRepository;
import com.personelyonetimsistemi.repository.PersonelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiseService {
    @Autowired
    private GiseRepository giseRepository;

    @Autowired
    private PersonelRepository personelRepository;


    public List<Gise> getAllGiseler(){

        return giseRepository.findAll();
    }


    public Gise getOneGise(Long giseId){
        return giseRepository.findById(giseId).orElse(null);
    }


    public Gise saveOneGise(Gise gise) {
        if(gise.getGiseAd() != null){
            return giseRepository.save(gise);
        }else{
            return null;
        }
    }

    public Gise updateOneGise(Long id, Gise yeniGise) {
        Optional<Gise> gise = giseRepository.findById(id);

        if(gise.isPresent()){
            Gise foundGise = gise.get();
            foundGise.setGiseAd(yeniGise.getGiseAd());
            return giseRepository.save(foundGise);
        }else{
            return null;
        }

    }

    public void deleteOneGise(Long giseId) {
        Gise gise = giseRepository.findById(giseId)
                .orElseThrow(() -> new EntityNotFoundException("Gişe bulunamadı!"));

        // İlişkili personellerin gişe alanını null yap
        List<Personel> personeller = personelRepository.findByGise(gise);
        for (Personel personel : personeller) {
            personel.setGise(null);
        }

        personelRepository.saveAll(personeller);

        giseRepository.delete(gise);
    }

    public ResponseEntity<String> passGise(Long girilmekIstenenGise, Personel personel) {
        Long personelinAitOlduguGise = personel.getGise().getGiseId();

        Optional<Personel> buPersonelVarMi = personelRepository.findById(personel.getPersonelId());

        Optional<Gise> buDegerVarMi = giseRepository.findById(girilmekIstenenGise);

        if(buPersonelVarMi.isPresent()){
            if(buDegerVarMi.isPresent()){
                if(personelinAitOlduguGise.equals(girilmekIstenenGise)){
                    return new ResponseEntity<>("Personel gişeye giriş yaptı!", HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>("Personelin bu gişeye girme yetkisi yok!", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>("Girmek istediğiniz gişe mevcut değil!", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Bu personel mevcut değil! Kurum dışı giriş yasak!", HttpStatus.BAD_REQUEST);
        }

    }

}
