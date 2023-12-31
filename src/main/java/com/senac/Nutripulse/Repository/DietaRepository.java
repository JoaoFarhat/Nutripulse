package com.senac.Nutripulse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.Nutripulse.Model.Dietas;

@Repository
public interface DietaRepository extends JpaRepository<Dietas, Integer> {

}
