package br.com.denys.api.controller;

import br.com.denys.domain.Credential;
import br.com.denys.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/auth")
    public ResponseEntity<String> auth(@RequestBody Credential credential) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword());

        try {
            Authentication authenticate = authenticationManager.authenticate(auth);

            String token = tokenService.createToken(authenticate);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
