package com.personelyonetimsistemi.service;

import com.personelyonetimsistemi.entity.Birim;
import com.personelyonetimsistemi.entity.Personel;
import com.personelyonetimsistemi.repository.BirimRepository;
import com.personelyonetimsistemi.repository.PersonelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BirimService {
    @Autowired
    private BirimRepository birimRepository;

    @Autowired
    private PersonelRepository personelRepository;


    /*
    public List<Birim> getAllBirimler(Optional<Integer> birimId) {
        if(birimId.isPresent()){
            return birimRepository.findByBirimId(birimId.get());
        }
        else{
            return birimRepository.findAll();
        }
    }
    */

    public List<Birim> getAllBirimler(){
        return birimRepository.findAll();
    }

/*

    public JSONObject getOneBirim(Long birimId) throws JSONException {
        JSONObject birimVePersoneli = new JSONObject();
      //  JSONObject birimJson= new JSONObject();
       Optional<Birim> birim = birimRepository.findById(birimId);
       // birimJson.put("birim",birim);

        JSONObject personelJson = new JSONObject();
        List<Personel> personelList = personelRepository.getPersonellerByBirimId(birimId);
        for (Personel p:personelList
             ) {
            personelJson.put("Personel",p);
        }

        birimVePersoneli.put("Birim",birim);
        birimVePersoneli.put("Personel",personelJson);
      //  return birimRepository.findById(String.valueOf(birimId)).orElse(null);
        return birimVePersoneli;
    }

*/

    public Birim getOneBirim(Long birimId){
        return birimRepository.findById(birimId).orElse(null);
    }


    public Birim saveOneBirim(Birim birim) {
        if(birim.getBirimAd() != null){
            return birimRepository.save(birim);
        }else{
            return null;
        }
    }

    public Birim updateOneBirim(Long id, Birim yeniBirim) {
        Optional<Birim> birim = birimRepository.findById(id);

        if(birim.isPresent()){
            Birim foundBirim = birim.get();
            foundBirim.setBirimAd(yeniBirim.getBirimAd());
            return birimRepository.save(foundBirim);
        }else{
            return null;
        }

    }

    public void deleteOneBirim(Long birimId) {
        Birim birim = birimRepository.findById(birimId)
                .orElseThrow(() -> new EntityNotFoundException("Birim bulunamadı!"));

        // İlişkili personellerin birim alanını null yap (gerekirse)
        List<Personel> personeller = personelRepository.findByBirim(birim);
        for (Personel personel : personeller) {
            personel.setBirim(null);
        }
        personelRepository.saveAll(personeller); // Değişiklikleri kaydet

        birimRepository.delete(birim);
    }

/*

    public void deleteOneBirim(Long birimId) {
        if (birimRepository.existsById(birimId)) { // Birim var mı kontrolü
            List<Personel> personeller = personelRepository.findAll();
            birimRepository.deleteById(birimId);
        } else {
            throw new EntityNotFoundException("Birim bulunamadı!");
        }
    }

*/
}
