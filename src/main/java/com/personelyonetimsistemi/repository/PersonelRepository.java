package com.personelyonetimsistemi.repository;

import com.personelyonetimsistemi.entity.Birim;
import com.personelyonetimsistemi.entity.Gise;
import com.personelyonetimsistemi.entity.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PersonelRepository extends JpaRepository<Personel, Long> {
    List<Personel> findByBirim(Birim birim);
    List<Personel> findByGise(Gise gise);
}
