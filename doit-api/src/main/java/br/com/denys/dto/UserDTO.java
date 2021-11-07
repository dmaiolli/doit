package br.com.denys.dto;

import br.com.denys.domain.Role;
import br.com.denys.domain.Task;
import br.com.denys.domain.User;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
public class UserDTO {

    private String name;
    private String email;
    private String password;

    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User transformToObject() {
        return new User(name, email, password);
    }


}
