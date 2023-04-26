package com.infotel.ali.alisscreenscorewebapp.services;

import com.infotel.ali.alisscreenscorewebapp.exceptions.LoginExceptions;
import com.infotel.ali.alisscreenscorewebapp.exceptions.RegistrationExceptions;
import com.infotel.ali.alisscreenscorewebapp.models.User;
import com.infotel.ali.alisscreenscorewebapp.dto.UserDTO;
import com.infotel.ali.alisscreenscorewebapp.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return convertToUserDTO(user);
        }
        return null;
    }

    public UserDTO createUser(String username, String password, String email) {
        // Check if username is already taken
        if (userRepository.existsByUsername(username)) {
            System.out.println("Username Taken");
            throw new RegistrationExceptions.UsernameTakenException("Username is already taken");
        }

        // Check if email is already taken
        if (userRepository.existsByEmail(email)) {
            System.out.println("Email Taken");
            throw new RegistrationExceptions.EmailTakenException("Email is already taken");
        }

        // Generate a random salt
        String salt = BCrypt.gensalt();

        // Hash the password with the salt
        String hashedPassword = BCrypt.hashpw(password, salt);

        // Create a new user entity
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword); // Store the hashed password in the user entity
        user.setSalt(salt); // Store the salt value in the user entity
        user.setEmail(email);
        user.setCreatedAt(Instant.now());
        userRepository.save(user);

        return convertToUserDTO(user);
    }


    public UserDTO loginUser(String username, String password) {
        // Retrieve the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new LoginExceptions.InvalidCredentialsException("Invalid username or password"));

        // Verify the password
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            // Passwords match, create and return UserDTO with JWT token

            return convertToUserDTO(user);
        } else {
            // Passwords do not match, throw InvalidCredentialsException
            throw new LoginExceptions.InvalidCredentialsException("Invalid username or password");
        }
    }

    // Method to convert User entity to UserDTO
    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
