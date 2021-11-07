package br.com.denys.api.controller;

import br.com.denys.domain.User;
import br.com.denys.repository.UserRepository;
import br.com.denys.service.AuthenticationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    private final UserRepository userRepository;

    public ApiUserController(UserRepository userService) {
        this.userRepository = userService;
    }

    @PostMapping
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<User> create(@RequestBody @Valid User user, UriComponentsBuilder uriComponentsBuilder) {
        user.setPassword(AuthenticationService.getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        URI uri = uriComponentsBuilder
                .path("/api/user/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        return (ResponseEntity<List<User>>) userRepository.findAll();
    }

}
