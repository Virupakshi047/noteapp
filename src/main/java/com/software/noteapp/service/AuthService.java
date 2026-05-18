package com.software.noteapp.service;

import com.software.noteapp.dto.AuthLoginRequestDTO;
import com.software.noteapp.dto.AuthLoginResponseDTO;
import com.software.noteapp.dto.AuthRequestDTO;
import com.software.noteapp.dto.AuthResponseDTO;
import com.software.noteapp.entity.User;
import com.software.noteapp.enums.UserEnums;
import com.software.noteapp.repository.UserRepository;
import com.software.noteapp.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder BCrypt;
    private JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder BCrypt,JwtUtil jwtUtil){
        this.BCrypt=BCrypt;
        this.userRepository=userRepository;
        this.jwtUtil=jwtUtil;
    }

    public AuthResponseDTO addUser(AuthRequestDTO authRequestDTO){
        try{
            Optional<User> user =  userRepository.findByEmailId(authRequestDTO.getEmail());
            if(!user.isEmpty()) return new AuthResponseDTO("","No need to register again");
            User user1 = new User();
            user1.setName(authRequestDTO.getName());
            user1.setUserRole(UserEnums.UserRoles.valueOf("USER"));
            String hashedpassword = BCrypt.encode(authRequestDTO.getPassword());
            user1.setPassword(hashedpassword);
            user1.setEmailId(authRequestDTO.getEmail());
            userRepository.save(user1);
            return  new AuthResponseDTO("","Registation succeeded you can login");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public AuthResponseDTO addAdmin(AuthRequestDTO authRequestDTO){
        try{
            Optional<User> user =  userRepository.findByEmailId(authRequestDTO.getEmail());
            if(!user.isEmpty()) return new AuthResponseDTO("","No need to register again");
            User user1 = new User();
            user1.setName(authRequestDTO.getName());
            user1.setUserRole(UserEnums.UserRoles.valueOf("ADMIN"));
            String hashedpassword = BCrypt.encode(authRequestDTO.getPassword());
            user1.setPassword(hashedpassword);
            user1.setEmailId(authRequestDTO.getEmail());
            userRepository.save(user1);
            return  new AuthResponseDTO("","Registation succeeded you can login");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private boolean chkPassword(String providedPassowrd,String storedHash){
        return BCrypt.matches(providedPassowrd,storedHash);
    }

    public AuthLoginResponseDTO loginUser(AuthLoginRequestDTO authLoginRequestDTO){
        Optional<User> user = userRepository.findByEmailId(authLoginRequestDTO.getEmailId());
        if (user.isEmpty()) return new AuthLoginResponseDTO("","Register yourself first");
        boolean isValidUser = chkPassword(authLoginRequestDTO.getPassword(),user.get().getPassword());
        if (!isValidUser) return new AuthLoginResponseDTO("","User name or passowrd is wrong");
        String newToken = jwtUtil.generateToken(user.get().getName(), String.valueOf(user.get().getUserRole()),user.get().getEmailId());
        return new AuthLoginResponseDTO(newToken,"Login success!!");
    }

}
