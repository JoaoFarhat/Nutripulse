package com.senac.Nutripulse.Repository;

import com.senac.Nutripulse.Entity.Treinos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreinosRepository extends JpaRepository<Treinos, Integer> {

}
