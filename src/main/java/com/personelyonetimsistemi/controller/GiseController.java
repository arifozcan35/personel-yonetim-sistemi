package com.personelyonetimsistemi.controller;

import com.personelyonetimsistemi.entity.Gise;
import com.personelyonetimsistemi.entity.Personel;
import com.personelyonetimsistemi.service.GiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gise")
public class GiseController {
    @Autowired
    private GiseService giseService;


    @GetMapping
    public List<Gise> getAllGiseler() {
        return giseService.getAllGiseler();
    }

    @GetMapping("/{giseId}")
    public Gise getOneGise(@PathVariable Long giseId) {
        return giseService.getOneGise(giseId);
    }

    @PostMapping
    public Gise createGise(@RequestBody Gise yeniGise) {
        return giseService.saveOneGise(yeniGise);
    }

    @PutMapping("/{giseId}")
    public Gise updateGise(@PathVariable Long giseId, @RequestBody Gise yeniGise) {
        return giseService.updateOneGise(giseId, yeniGise);
    }

    @DeleteMapping("/{giseId}")
    public void deleteGise(@PathVariable Long giseId) {
        giseService.deleteOneGise(giseId);
    }

    @PostMapping("/{girilmekIstenenGise}")
    public ResponseEntity<String> passGise(@PathVariable Long girilmekIstenenGise, @RequestBody Personel personel){
        return giseService.passGise(girilmekIstenenGise, personel);
    }
}
