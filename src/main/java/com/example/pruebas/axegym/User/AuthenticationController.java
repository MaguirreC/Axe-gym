package com.example.pruebas.axegym.User;

import com.example.pruebas.axegym.security.dto.AuthenticationRequest;
import com.example.pruebas.axegym.security.dto.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){

            AuthenticationResponse jwtDto =authenticationService.login(authenticationRequest);
            return ResponseEntity.ok(jwtDto);
    }

    @GetMapping("/public-access")
    public String endPublico(){
            return "ewjfierjfirf";
    }
}
