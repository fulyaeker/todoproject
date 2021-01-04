package com.todoproject.service;

import com.todoproject.entity.User;
import com.todoproject.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public
class UserService {

    @Autowired
    private UserRepository repo;


    public List<User> listAll(){

        return (List<User>) repo.findAll();

    }

    public Long getCurrentUserId(String username){
        return repo.getUserByUsername(username).getId();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }



}