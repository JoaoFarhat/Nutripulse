package com.senac.Nutripulse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.Nutripulse.Model.Aliments;

import java.util.List;

@Repository
public interface AlimentsRepository extends JpaRepository<Aliments, Integer> {

    List<Aliments> findByDieta_id(Integer idDieta);
}
