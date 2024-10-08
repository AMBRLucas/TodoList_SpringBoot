package com.lucas.todoSimple.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import com.lucas.todoSimple.repositories.TaskRepositorie;
import com.lucas.todoSimple.models.Task;
import com.lucas.todoSimple.models.User;

@Service
public class TaskService {
    
    @Autowired    
    private TaskRepositorie taskRepositorie;

    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Optional<Task> task = this.taskRepositorie.findById(id);

        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa não encontrada! ID: " + id + " Tipo: " + Task.class.getName()
        ));
    }   

    public List<Task> findAllByUserId(Long id){
        List<Task> tasks = this.taskRepositorie.findByUser_id(id);
        return tasks;
    }

    @Transactional
    public Task Create(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepositorie.save(obj);

        return obj;
    }

    @Transactional
    public Task Update(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());

        return this.taskRepositorie.save(newObj);
    }

    public void Delete(Long id){
        findById(id);
        try {
            this.taskRepositorie.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possivel excluir pois a entidades relacionadas!");
        }
    }
}
