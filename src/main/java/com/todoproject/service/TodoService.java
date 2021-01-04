package com.todoproject.service;


import com.todoproject.entity.Todo;
import com.todoproject.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService{

    @Autowired
    private TodoRepository repo;

    public List<Todo> listAll(){

        return repo.findAll();
    }

    public List<Todo> listById(Long id){

        return repo.findByUserId(id);
    }
    public List<Todo> listById_DateAsc(Long id){

        return repo.findByUserId_DateAsc(id);
    }


    public List<Todo> listAllByDateAsc(){

        return repo.findAll(Sort.by(Sort.Direction.ASC,"todo_date"));
    }

    public List<Todo> listAllByDateDesc()
    {
        return repo.findAll(Sort.by(Sort.Direction.DESC,"todo_date"));
    }

    public void save(Todo todo) {

        repo.save(todo);
    }

    public Todo get(Long id) {

        return repo.findById(id).get();
    }

    public void delete(Long id) {

        repo.deleteById(id);
    }

}