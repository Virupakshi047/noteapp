package com.software.noteapp.controller;

import com.software.noteapp.dto.AuthLoginRequestDTO;
import com.software.noteapp.dto.AuthLoginResponseDTO;
import com.software.noteapp.dto.AuthRequestDTO;
import com.software.noteapp.dto.AuthResponseDTO;
import com.software.noteapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/register")
    public AuthResponseDTO registerUser(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.addUser(authRequestDTO);
    }
    @PostMapping("/add-admin")
    public AuthResponseDTO registerAdmin(@RequestBody AuthRequestDTO authRequestDTO){
        return authService.addAdmin(authRequestDTO);
    }

    @PostMapping("/login")
    public AuthLoginResponseDTO loginUser(@RequestBody AuthLoginRequestDTO authLoginRequestDTO){
        return authService.loginUser(authLoginRequestDTO);
    }
}
