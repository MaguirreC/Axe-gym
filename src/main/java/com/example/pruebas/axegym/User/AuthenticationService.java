package com.example.pruebas.axegym.User;

import com.example.pruebas.axegym.security.JwtService;
import com.example.pruebas.axegym.security.dto.AuthenticationRequest;
import com.example.pruebas.axegym.security.dto.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest authRequest){
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword());

        authenticationManager.authenticate(authenticationToken);
        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(()->new RuntimeException("user not found"));

        String jwt = jwtService.generateToken(user,generateExtraClaims(user));

        return new AuthenticationResponse(jwt);
    }


    private Map<String,Object> generateExtraClaims(User user) {
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("name",user.getName());
        extraClaims.put("role",user.getRole().name());
        extraClaims.put("permissions",user.getAuthorities());
        return extraClaims;
    }
}
