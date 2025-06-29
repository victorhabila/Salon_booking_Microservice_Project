package com.projsalony.mapper;

import com.projsalony.model.User;
import com.projsalony.payload.response.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setRole(user.getRole().toString());

        return userDTO;
    }

}
