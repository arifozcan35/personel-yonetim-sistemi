package com.personelyonetimsistemi.repository;

import com.personelyonetimsistemi.entity.Mesai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaiRepository extends JpaRepository<Mesai, Long> {
    //List<Mesai> findByPersonel(Personel personel);
}
