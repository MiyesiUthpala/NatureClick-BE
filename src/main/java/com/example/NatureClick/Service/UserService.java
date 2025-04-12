package com.example.NatureClick.Service;

import com.example.NatureClick.Dto.LoginDTO;
import com.example.NatureClick.Dto.UserDTO;
import com.example.NatureClick.Entity.User;
import com.example.NatureClick.response.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    String addUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    LoginResponse loginUser(LoginDTO loginDTO);
    List<User> getRecommendedUsers(int userId);
    User getUserById(int userId);
}
