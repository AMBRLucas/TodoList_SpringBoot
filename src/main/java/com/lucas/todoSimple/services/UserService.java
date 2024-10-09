package com.lucas.todoSimple.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import com.lucas.todoSimple.models.User;
import com.lucas.todoSimple.repositories.UserRepositorie;
import com.lucas.todoSimple.services.exceptions.DataBindingViolationException;
import com.lucas.todoSimple.services.exceptions.ObjectNotFoundException;


@Service
public class UserService {

    @Autowired
    private UserRepositorie userRepositorie;

    public User findById(Long id){
        Optional<User> user = this.userRepositorie.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException(
            "Usuario não encontrado! ID: " + id + "Tipo: " + User.class.getName()
        ));
    }

    
    @Transactional
    public User Create(User obj){
        obj.setId(null);
        obj = this.userRepositorie.save(obj);

        return obj;
    }

    @Transactional
    public User Update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());

        return this.userRepositorie.save(newObj);
    }

    public void Delete(Long id){
        findById(id);
        try{
            this.userRepositorie.deleteById(id);
        }catch(Exception e){
            throw new DataBindingViolationException("Não é possivel excluir pois a entidades relacionadas!");
        }
    }
}
