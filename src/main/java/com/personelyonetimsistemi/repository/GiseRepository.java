package com.personelyonetimsistemi.repository;

import com.personelyonetimsistemi.entity.Gise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GiseRepository extends JpaRepository<Gise, Long> {

}
