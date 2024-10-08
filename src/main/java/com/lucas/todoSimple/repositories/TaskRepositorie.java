package com.lucas.todoSimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.todoSimple.models.Task;

/*
 ? Repositorys são os mediadores entre o BD e a aplicação disponibilizando as funções para buscar, e registrar dados
 ? Esse é o repository de Tasks, nele estão as conexões com o banco relacionadas as tarefas
*/

//!  Annotation para definir a classe como um repository
@Repository
public interface TaskRepositorie extends JpaRepository<Task, Long>{
    
    List<Task> findByUser_id(Long id);

}
