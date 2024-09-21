package com.personelyonetimsistemi.controller;

import com.personelyonetimsistemi.entity.Personel;
import com.personelyonetimsistemi.service.MesaiService;
import com.personelyonetimsistemi.service.PersonelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personel")
public class PersonelController {
    @Autowired
    private PersonelService personelService;

    @Autowired
    private MesaiService mesaiService;

    @GetMapping
    public List<Personel> getAllPersoneller() {
        return personelService.getAllPersoneller();
    }

    @GetMapping("/{personelId}")
    public Personel getAOnePersonel(@PathVariable Long personelId) {
        return personelService.getAOnePersonel(personelId);
    }



    @PostMapping
    public ResponseEntity<String> createPersonel(@RequestBody Personel yeniPersonel) {
        return personelService.saveOnePersonel(yeniPersonel);
    }

    @PutMapping("/{personelId}")
    public ResponseEntity<String> updatePersonel(@PathVariable Long personelId, @RequestBody Personel yeniPersonel) {
        return personelService.updateOnePersonel(personelId, yeniPersonel);
    }

    @DeleteMapping("/{personelId}")
    public void deletePersonel(@PathVariable Long personelId) {
        personelService.deleteOnePersonel(personelId);
    }

    @GetMapping("/maas/{personelId}")
    public void seeMaas(@PathVariable Long personelId) {
        personelService.mesaiHesapla(personelId);
    }
}
