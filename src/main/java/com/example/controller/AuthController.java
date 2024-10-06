package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.LoginDto;
import com.example.entity.User;
import com.example.jwt.TokenProvider;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("reg")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.save(userDto);
        return ResponseEntity.ok("Registration was successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        User user = userService.findByUsername(loginDto.getUsername());

        if (new BCryptPasswordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
            String token = tokenProvider.generateToken(user.getId(), user.getUsername(), user.getPassword(), user.getRoles());
            return ResponseEntity.ok(token);
        }
        throw new RuntimeException();

    }

}
