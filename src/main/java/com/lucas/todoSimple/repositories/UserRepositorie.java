package com.lucas.todoSimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.todoSimple.models.User;

/*
 ? Repositorys são os mediadores entre o BD e a aplicação disponibilizando as funções para buscar, e registrar dados
 ? Esse é o repository de User, nele estão as conexões com o banco relacionadas aos usuarios
*/

//!  Annotation para definir a classe como um repository
@Repository
public interface UserRepositorie extends JpaRepository<User, Long> {
    
}
