package com.example.NatureClick.Controller;


import com.example.NatureClick.Dto.LoginDTO;
import com.example.NatureClick.Dto.UserDTO;
import com.example.NatureClick.Service.UserService;
import com.example.NatureClick.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/save")
    public String saveEmployee(@RequestBody UserDTO userDTO)
    {
        String id = userService.addUser(userDTO);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse  = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }






}
