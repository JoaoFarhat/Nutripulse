package com.senac.Nutripulse.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.Nutripulse.Model.Users;

//Não pude utilizar crud repository pois foi necessario utilizar os recursos do JPA Repositoy
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);
    
}
