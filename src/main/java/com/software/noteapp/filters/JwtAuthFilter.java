package com.software.noteapp.filters;

import com.software.noteapp.entity.User;
import com.software.noteapp.repository.UserRepository;
import com.software.noteapp.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtUtil jwtUtil , UserRepository userRepository){
        this.jwtUtil=jwtUtil;
        this.userRepository=userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println("INSIDE doFilterINternal thing excute while login");
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);

        if(!jwtUtil.isTokenValid(token)){
            filterChain.doFilter(request,response);
            return;
        }
        String emailId = jwtUtil.extractEmailId(token);

        Optional<User> user = userRepository.findByEmailId(emailId);
        if(user.isEmpty()){
            System.out.println("User not found");
            filterChain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(emailId,null, List.of(new SimpleGrantedAuthority(user.get().getUserRole().name())));
        System.out.println("AUTH TOKEN:");
        System.out.println(authToken);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
