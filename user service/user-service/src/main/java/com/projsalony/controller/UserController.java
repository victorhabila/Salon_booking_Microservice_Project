package com.projsalony.controller;

import com.projsalony.exception.UserException;
import com.projsalony.model.User;
import com.projsalony.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

    @PostMapping("/api/user")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED) ;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> hetUserById(@PathVariable Long id) throws UserException {
    User user = userService.getUserById(id);
    return new ResponseEntity<>(user, HttpStatus.OK);

    }
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable  Long id) throws Exception {
    User updatedUser = userService.getUserById(id);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws UserException {
        userService.deleteUser(id);
        return new ResponseEntity<>("User is deleted", HttpStatus.ACCEPTED);
    }
}
