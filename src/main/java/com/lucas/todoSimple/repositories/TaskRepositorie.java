package com.lucas.todoSimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.todoSimple.models.Task;

@Repository
public interface TaskRepositorie extends JpaRepository<Task, Long>{
    
    List<Task> findByUser_id(Long id);

}
