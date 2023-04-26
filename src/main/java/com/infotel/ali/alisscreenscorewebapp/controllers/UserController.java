package com.infotel.ali.alisscreenscorewebapp.controllers;

import com.infotel.ali.alisscreenscorewebapp.dto.UserDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.UserLoginDTO;
import com.infotel.ali.alisscreenscorewebapp.dto.UserRegistrationDTO;
import com.infotel.ali.alisscreenscorewebapp.exceptions.LoginExceptions;
import com.infotel.ali.alisscreenscorewebapp.exceptions.RegistrationExceptions;
import com.infotel.ali.alisscreenscorewebapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Mapping to get UserDTO by ID
    @GetMapping("/users/{id}")
    public UserDTO getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO registrationDto) {
        try {
            UserDTO userDto = userService.createUser(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getEmail());
            return ResponseEntity.ok(userDto);
        } catch (RegistrationExceptions.UsernameTakenException ex) {
            return ResponseEntity.badRequest().body("Username is already taken");
        } catch (RegistrationExceptions.EmailTakenException ex) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDto,  HttpSession session) {
        try {
            UserDTO userDto = userService.loginUser(loginDto.getUsername(), loginDto.getPassword());
            session.setAttribute("user", userDto);
            System.out.println(session.getAttribute("user").toString());
            return ResponseEntity.ok().build();
        } catch (LoginExceptions.InvalidCredentialsException ex) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
         session.invalidate();
    }

    @GetMapping("/user")
    public ResponseEntity<?> userInfo(HttpSession session) {
        // Retrieve user from session
        UserDTO userDto = (UserDTO) session.getAttribute("user");
        if (userDto != null) {
            // Return user as JSON response
            return ResponseEntity.ok(userDto);
        } else {
            // Return error response if user is not found in session
            return ResponseEntity.badRequest().body("User not found in session");
        }
    }

}