package com.projsalony.service.Impl;

import com.projsalony.exception.UserException;
import com.projsalony.model.User;
import com.projsalony.payload.response.dto.KeycloakUserDTO;
import com.projsalony.payload.response.dto.KeycloakUserinfo;
import com.projsalony.repository.UserRepository;
import com.projsalony.service.KeycloakUserService;
import com.projsalony.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakUserService keycloakUserService;
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        throw new UserException("User not found");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) throws UserException {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isEmpty()){
            throw new UserException("user not found with id" + id);
        }
        userRepository.deleteById(existingUser.get().getId());

    }

    @Override
    public User updateUser(User user, Long id) throws UserException {
        Optional<User> user1 = userRepository.findById(id); // note optional provides us with additional methods like isPresent, isEmpty etc
        if(user1.isEmpty()){
            throw new UserException("user not found with id" + id);
        }
        User existingUser = user1.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());
        existingUser.setUsername(user.getUsername());
        return userRepository.save(existingUser);
    }

    @Override
    public User getUserFromJwt(String jwt) throws Exception {
        KeycloakUserinfo keycloakUserinfo = keycloakUserService.fetchUserProfileByJwt(jwt);

        if (keycloakUserinfo == null || keycloakUserinfo.getEmail() == null) {
            throw new UserException("Invalid token or missing email in user profile");
        }

        User user = userRepository.findByEmail(keycloakUserinfo.getEmail());
        if (user == null) {
            throw new UserException("User not found with email: " + keycloakUserinfo.getEmail());
        }
        return user;
    }
}
