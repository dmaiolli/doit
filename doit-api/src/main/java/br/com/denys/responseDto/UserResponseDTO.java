package br.com.denys.responseDto;

import br.com.denys.domain.Role;
import br.com.denys.domain.Task;
import br.com.denys.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private List<Task> tasks;

    public static UserResponseDTO transformToResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getTasks());
    }

}
