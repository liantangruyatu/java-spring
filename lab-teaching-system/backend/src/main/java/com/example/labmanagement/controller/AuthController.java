package com.example.labmanagement.controller;

import com.example.labmanagement.model.User;
import com.example.labmanagement.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                response.put("success", true);
                response.put("id", user.getId());
                response.put("username", user.getUsername());
                response.put("fullname", user.getFullname());
                response.put("role", user.getRole());
                response.put("email", user.getEmail());
            }
        } catch (AuthenticationException e) {
            response.put("success", false);
            response.put("message", "用户名或密码错误");
        }
        return response;
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        Map<String, Object> m = new HashMap<>();
        if (authentication == null) return m;
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            m.put("id", user.getId());
            m.put("username", user.getUsername());
            m.put("fullname", user.getFullname());
            m.put("role", user.getRole());
            m.put("email", user.getEmail());
        }
        return m;
    }
}
