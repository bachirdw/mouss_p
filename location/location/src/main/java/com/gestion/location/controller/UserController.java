package com.gestion.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.gestion.location.model.User;
import com.gestion.location.model.UserDTO;
import com.gestion.location.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200") // ? autoriser Angular
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  
  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserController(UserService userService) {
    this.userService = userService;
  }


@PostMapping("/register")
@CrossOrigin(origins = "http://localhost:4200")
public ResponseEntity<User> registerUser(@RequestBody User user) {
    // Empêche de créer un utilisateur avec un rôle ADMIN depuis le frontend
    user.setRole("USER");

    // Encodage du mot de passe
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Sauvegarde
    User savedUser = userService.saveUser(user);
    return ResponseEntity.ok(savedUser);
}

// Map User to DTO (UserController.java)
@GetMapping
@PreAuthorize("hasRole('ADMIN')")
public List<UserDTO> getAllUsers() {
    return userService.getAllUsers().stream()
        .map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail(), u.getRole()))
        .collect(Collectors.toList());
}
}
