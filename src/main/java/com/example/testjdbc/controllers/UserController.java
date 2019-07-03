package com.example.testjdbc.controllers;

import java.util.List;

import com.example.testjdbc.entities.User;
import com.example.testjdbc.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value="/users", produces="application/json")
    public List<User> displayUsers() {
        return userRepository.getAllUsers();
    }

    @GetMapping(path="/users_emails", produces="application/json")
    public List<User> displayUsersByEmail(@RequestParam String email){
        return userRepository.getUsersByEmail(email);
    }

    @PostMapping(value="/create_user", produces="application/json")
    public User createUser(@RequestBody User user) {
        userRepository.createUser(user);
        return user;
    }

    @PostMapping(path="/users/{id}", produces="application/json")
    public User updateUser(@RequestBody User user, @PathVariable("id") int id){
        userRepository.updateUser(user, id);
        return user;
    }

    @GetMapping(path="/delete_user")
    public void deleteUser(){
        userRepository.deleteUser(7);
    }

    

}