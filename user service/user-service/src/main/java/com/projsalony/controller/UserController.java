package com.projsalony.controller;

import com.projsalony.model.User;
import com.projsalony.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/api/user")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED) ;
    }

    @GetMapping("/api/users")
    public List<User> getUser(){
        return userRepository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public User hetUserById(@PathVariable Long id) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        throw new Exception("User not found");

    }
    @PutMapping("/api/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable  Long id) throws Exception {

        Optional<User> user1 = userRepository.findById(id); // note optional provides us with additional methods like isPresent, isEmpty etc
        if(user1.isEmpty()){
            throw new Exception("user not found with id" + id);
        }
        User existingUser = user1.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    @DeleteMapping("/api/users/{id}")
    public String deleteUserById(@PathVariable Long id) throws Exception {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isEmpty()){
            throw new Exception("user not found with id" + id);
        }
        userRepository.deleteById(existingUser.get().getId());
        return "User deleted";

    }
}
