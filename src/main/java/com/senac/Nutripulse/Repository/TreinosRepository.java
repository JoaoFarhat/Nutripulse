package com.senac.Nutripulse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.Nutripulse.Model.Treinos;

@Repository
public interface TreinosRepository extends JpaRepository<Treinos, Integer> {

}
