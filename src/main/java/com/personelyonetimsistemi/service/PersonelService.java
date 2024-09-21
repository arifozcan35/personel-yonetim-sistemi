package com.personelyonetimsistemi.service;

import com.personelyonetimsistemi.entity.Birim;
import com.personelyonetimsistemi.entity.Gise;
import com.personelyonetimsistemi.entity.Mesai;
import com.personelyonetimsistemi.entity.Personel;
import com.personelyonetimsistemi.repository.BirimRepository;
import com.personelyonetimsistemi.repository.GiseRepository;
import com.personelyonetimsistemi.repository.MesaiRepository;
import com.personelyonetimsistemi.repository.PersonelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonelService {
    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private MesaiRepository mesaiRepository;

    @Autowired
    private MesaiService mesaiService;

    @Autowired
    private BirimRepository birimRepository;

    @Autowired
    private GiseRepository giseRepository;

    public List<Personel> getAllPersoneller() {
        return personelRepository.findAll();
    }

    public Personel getAOnePersonel(Long personelId) {
        return personelRepository.findById(personelId).orElse(null);
    }

/*

    public ResponseEntity<String> saveOnePersonel(Personel yeniPersonel) {
        if (yeniPersonel.getBirim() == null) {
            // Hata mesajını ve HTTP durum kodunu içeren bir ResponseEntity oluştur
            return new ResponseEntity<>("Personel kaydedilemedi! Lütfen personel birimini giriniz.", HttpStatus.BAD_REQUEST);
        } else {

            Personel kaydedilenPersonel = personelRepository.save(yeniPersonel);
            return new ResponseEntity<>("Personel başarıyla kaydedildi!", HttpStatus.CREATED);
        }
    }
*/

    public ResponseEntity<String> saveOnePersonel(Personel yeniPersonel) {

        // Birim kontrolü (zorunlu bir alan)
        if (yeniPersonel.getBirim() != null && yeniPersonel.getBirim().getBirimId() != null) {
            Birim existingBirim = birimRepository.findById(yeniPersonel.getBirim().getBirimId())
                    .orElse(null);
            if (existingBirim == null) {
                return new ResponseEntity<>("Uygun bir birim seçmediniz!", HttpStatus.BAD_REQUEST);
            }
            yeniPersonel.setBirim(existingBirim);
        } else {
            return new ResponseEntity<>("Personel kaydedilemedi! Lütfen personel birimini giriniz.", HttpStatus.BAD_REQUEST);
        }


        // Gise kontrolü (zorunlu bir alan)
        if (yeniPersonel.getGise() != null && yeniPersonel.getGise().getGiseId() != null) {
            Gise existingGise = giseRepository.findById(yeniPersonel.getGise().getGiseId())
                    .orElse(null);
            if (existingGise == null) {
                return new ResponseEntity<>("Belirtilen gise bulunamadı!", HttpStatus.BAD_REQUEST);
            }
            yeniPersonel.setGise(existingGise);
        }else {
            return new ResponseEntity<>("Personel kaydedilemedi! Lütfen personel gişesini giriniz.", HttpStatus.BAD_REQUEST);
        }


        // Maaş değerini atama
        if(yeniPersonel.getYonetici() == null && yeniPersonel.getMaas() == null){
            return new ResponseEntity<>("Personel kaydedilemedi! Personelin yönetici veya maaş değerlerinden " +
                    "en az biri seçili olmalıdır.", HttpStatus.BAD_REQUEST);
        }else {
            if(yeniPersonel.getYonetici() != null){
                Personel pYonetici = new Personel(yeniPersonel.getYonetici());
                yeniPersonel.setMaas(pYonetici.getMaas());
            }else if (yeniPersonel.getYonetici() == null){
                Personel pMaas = new Personel(yeniPersonel.getMaas());
                yeniPersonel.setYonetici(pMaas.getYonetici());
                yeniPersonel.setMaas(pMaas.getMaas());
            }
        }


        personelRepository.save(yeniPersonel);
        // Personel kaydedilenPersonel = personelRepository.save(yeniPersonel);


        // Mesai kaydı
        if (yeniPersonel.getMesai() != null) {
            LocalTime giris = yeniPersonel.getMesai().getGirisSaati();
            LocalTime cikis = yeniPersonel.getMesai().getCikisSaati();
            if (giris != null && cikis != null && cikis.isAfter(giris)) {
                Mesai kaydedilenMesai = mesaiRepository.save(yeniPersonel.getMesai());
                yeniPersonel.setMesai(kaydedilenMesai);
            } else {
                return new ResponseEntity<>("Geçersiz giriş/çıkış saati!", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("Personel başarıyla kaydedildi!", HttpStatus.CREATED);

    }


    public ResponseEntity<String> updateOnePersonel(Long id, Personel yeniPersonel) {
        Optional<Personel> personel = personelRepository.findById(id);

        if (personel.isPresent()) {
            Personel foundPersonel = personel.get();

            if (yeniPersonel.getIsim() != null) {
                foundPersonel.setIsim(yeniPersonel.getIsim());
            }
            if (yeniPersonel.getEmail() != null) {
                foundPersonel.setEmail(yeniPersonel.getEmail());
            }


            if(yeniPersonel.getYonetici() != null){
                foundPersonel.setYonetici(yeniPersonel.getYonetici());

                Personel pYonetici = new Personel(yeniPersonel.getYonetici());
                foundPersonel.setMaas(pYonetici.getMaas());
            }
            else if(yeniPersonel.getMaas() != null){
                Personel pMaas = new Personel(yeniPersonel.getMaas());
                foundPersonel.setYonetici(pMaas.getYonetici());
                foundPersonel.setMaas(pMaas.getMaas());
            }

            // Birim kontrolü
            if (yeniPersonel.getBirim() != null) {
                Birim existingBirim = birimRepository.findById(yeniPersonel.getBirim().getBirimId())
                        .orElse(null);
                if (existingBirim == null) {
                    return new ResponseEntity<>("Uygun bir birim seçmediniz!", HttpStatus.BAD_REQUEST);
                }
                foundPersonel.setBirim(existingBirim);
            }


            // Gise kontrolü
            if (yeniPersonel.getGise() != null) {
                Gise existingGise = giseRepository.findById(yeniPersonel.getGise().getGiseId())
                        .orElse(null);
                if (existingGise == null) {
                    return new ResponseEntity<>("Belirtilen gise bulunamadı!", HttpStatus.BAD_REQUEST);
                }
                foundPersonel.setGise(existingGise);
            }

            // Mesai kaydı
            if (yeniPersonel.getMesai() != null) {
                LocalTime giris = yeniPersonel.getMesai().getGirisSaati();
                LocalTime cikis = yeniPersonel.getMesai().getCikisSaati();

                Long oncekiKayit = foundPersonel.getMesai().getMesaiId();

                if (giris != null && cikis != null && cikis.isAfter(giris)) {

                    // yeni mesai kaydı ekleme
                    Mesai kaydedilenMesai = mesaiRepository.save(yeniPersonel.getMesai());
                    foundPersonel.setMesai(kaydedilenMesai);

                    // önceki mesai kaydını silme
                    mesaiRepository.deleteById(oncekiKayit);
                }
            }

            personelRepository.save(foundPersonel);

            return new ResponseEntity<>("Personel başarıyla güncellendi!", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Personel bulunamadı!", HttpStatus.NOT_FOUND);
        }

    }


    public void deleteOnePersonel(Long id) {
        Optional<Personel> personel = personelRepository.findById(id);
        if (personel.isPresent()) {
            // Önce Mesai kaydını sil
            if (personel.get().getMesai() != null) {
                mesaiRepository.deleteById(personel.get().getMesai().getMesaiId());
            }
            // Sonra personel kaydını sil
            personelRepository.deleteById(id);
            System.out.println("Personel başarıyla silindi.");
        } else {
            System.out.println("Personel bulunamadı.");
        }
    }


    public void mesaiHesapla(Long personelId) {
        mesaiService.mesaiHesapla(personelId);
    }

}
