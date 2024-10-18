package com.lucas.todoSimple.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lucas.todoSimple.models.User;
import com.lucas.todoSimple.models.enums.ProfileEnum;
import com.lucas.todoSimple.repositories.UserRepositorie;
import com.lucas.todoSimple.services.exceptions.DataBindingViolationException;
import com.lucas.todoSimple.services.exceptions.ObjectNotFoundException;


@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        obj = this.userRepositorie.save(obj);

        return obj;
    }

    @Transactional
    public User Update(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));

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
