package com.example.NatureClick.Controller;


import com.example.NatureClick.Dto.LoginDTO;
import com.example.NatureClick.Dto.UserDTO;
import com.example.NatureClick.Entity.User;
import com.example.NatureClick.Service.UserService;
import com.example.NatureClick.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/save")
    public String saveEmployee(@RequestBody UserDTO userDTO)
    {
        return userService.addUser(userDTO);
    }

    @PostMapping(path = "/update")
    public void updateUser(@RequestBody UserDTO userDTO)
    {
        userService.updateUser(userDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse  = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/recommended/{id}")
    public ResponseEntity<List<User>> getRecommendedPosts(@PathVariable int id) {
        List<User> users = userService.getRecommendedUsers(id);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

}
