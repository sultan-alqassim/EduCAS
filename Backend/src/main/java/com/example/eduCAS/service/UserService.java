package com.example.eduCAS.service;

import com.example.eduCAS.repository.UserRepository;
import com.example.eduCAS.security.JwtUtil;
import com.example.eduCAS.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(String fullName, String email, String rawPassword){
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email already registered");
        }
        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(fullName, email, hashedPassword);
        userRepository.save(user);
        return jwtUtil.generateToken(email);
    }

    public String login(String email, String rawPassword){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if(!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("invalid email or password");
        }
        return jwtUtil.generateToken(email);
    }

}
