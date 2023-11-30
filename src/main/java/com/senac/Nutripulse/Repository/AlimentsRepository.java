package com.senac.Nutripulse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.Nutripulse.Entity.Aliments;

@Repository
public interface AlimentsRepository extends JpaRepository<Aliments, Integer> {

}
