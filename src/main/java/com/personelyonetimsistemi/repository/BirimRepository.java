package com.personelyonetimsistemi.repository;

import com.personelyonetimsistemi.entity.Birim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BirimRepository extends JpaRepository<Birim, Long> {

}
