package com.projsalony.service;

import com.projsalony.exception.UserException;
import com.projsalony.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void deleteUser(Long id) throws UserException;

    User updateUser(User user, Long id) throws Exception;
    User getUserFromJwt(String jwt) throws Exception;
}
