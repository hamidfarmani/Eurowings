package com.eurowings.newsletter.service;

import com.eurowings.newsletter.model.User;
import com.eurowings.newsletter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> retrieveUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }
}
