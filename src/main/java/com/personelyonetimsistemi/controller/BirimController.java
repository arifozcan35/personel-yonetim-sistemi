package com.personelyonetimsistemi.controller;

import com.personelyonetimsistemi.entity.Birim;
import com.personelyonetimsistemi.service.BirimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/birim")
public class BirimController {
    @Autowired
    private BirimService birimService;

    @GetMapping
    public List<Birim> getAllBirimler() {
        return birimService.getAllBirimler();
    }

/*
    @GetMapping("/{birimId}")
    public ResponseEntity<JSONObject> getBirimAndBirimPersonel(@PathVariable Long birimId) throws JSONException {
        return ResponseEntity.ok(birimService.getOneBirim(birimId));
    }
*/

    @GetMapping("/{birimId}")
    public Birim getOneBirim(@PathVariable Long birimId) {
        return birimService.getOneBirim(birimId);
}

    @PostMapping
    public Birim createBirim(@RequestBody Birim yeniBirim) {
        return birimService.saveOneBirim(yeniBirim);
    }

    @PutMapping("/{birimId}")
    public Birim updateBirim(@PathVariable Long birimId, @RequestBody Birim yeniBirim) {
        return birimService.updateOneBirim(birimId, yeniBirim);
    }
    @DeleteMapping("/{birimId}")
    public void deleteBirim(@PathVariable Long birimId) {
        birimService.deleteOneBirim(birimId);
    }
}
