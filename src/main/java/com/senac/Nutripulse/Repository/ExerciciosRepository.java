package com.senac.Nutripulse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senac.Nutripulse.Model.Exercicios;

import java.util.List;

public interface ExerciciosRepository extends JpaRepository<Exercicios, Integer> {
    List<Exercicios> findByTreino_id(Integer idTreinos);
}
