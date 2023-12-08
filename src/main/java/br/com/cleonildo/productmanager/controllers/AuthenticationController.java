package br.com.cleonildo.productmanager.controllers;

import br.com.cleonildo.productmanager.dto.RegisterRequest;
import br.com.cleonildo.productmanager.dto.UserAuthenticationRequest;
import br.com.cleonildo.productmanager.entities.User;
import br.com.cleonildo.productmanager.repositories.IUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, IUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserAuthenticationRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        if (this.userRepository.findByLogin(request.login()) != null )
            return ResponseEntity.badRequest().build();

        var encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        var newUser = new User(request.login(), encryptedPassword, request.role());
        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
