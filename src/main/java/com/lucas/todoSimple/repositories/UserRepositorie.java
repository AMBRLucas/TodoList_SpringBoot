package com.lucas.todoSimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.todoSimple.models.User;

@Repository
public interface UserRepositorie extends JpaRepository<User, Long> {
    
}
