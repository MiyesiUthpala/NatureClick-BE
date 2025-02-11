package com.example.NatureClick.Service.Implementation;

import com.example.NatureClick.Dto.LoginDTO;
import com.example.NatureClick.Dto.UserDTO;
import com.example.NatureClick.Entity.User;
import com.example.NatureClick.Repository.UserRepository;
import com.example.NatureClick.Service.UserService;
import com.example.NatureClick.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public String addUser(UserDTO userDTO) {

        User user = new User(
                userDTO.getUserid(),
                userDTO.getUsername(),
                userDTO.getEmail(),

                this.passwordEncoder.encode(userDTO.getPassword())
        );

        userRepository.save(user);

        return user.getUsername();
    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        String msg = "";
        User user1 = userRepository.findByEmail(loginDTO.getEmail());
        if (user1 != null){
                String password = loginDTO.getPassword();
                String encodedPassword = user1.getPassword();
                Boolean isPwRight = passwordEncoder.matches(password, encodedPassword);
                if (isPwRight){
                    Optional <User> user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                    if(user.isPresent()){
                        return  new LoginResponse("Login Success",true);
                    } else {
                        return new LoginResponse("Login Failed", false);
                    }
                } else {
                    return new LoginResponse("Password Not Match", false);
                }
        } else {
            return new LoginResponse("Email Not exits", false);
        }
    }
}
