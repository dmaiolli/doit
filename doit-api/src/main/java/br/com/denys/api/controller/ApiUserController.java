package br.com.denys.api.controller;

import br.com.denys.domain.User;
import br.com.denys.dto.UserDTO;
import br.com.denys.responseDto.UserResponseDTO;
import br.com.denys.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    private final UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriComponentsBuilder) {
        User user = userService.save(userDTO.transformToObject());
        URI uri = uriComponentsBuilder
                .path("/api/user/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(UserResponseDTO.transformToResponseDTO(user));
    }
}
