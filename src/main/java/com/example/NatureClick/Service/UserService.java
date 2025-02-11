package com.example.NatureClick.Service;

import com.example.NatureClick.Dto.LoginDTO;
import com.example.NatureClick.Dto.UserDTO;
import com.example.NatureClick.response.LoginResponse;
import org.springframework.stereotype.Service;


public interface UserService {

    String addUser(UserDTO userDTO);
    LoginResponse loginUser(LoginDTO loginDTO);
}
