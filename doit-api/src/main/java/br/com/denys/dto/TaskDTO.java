package br.com.denys.dto;

import br.com.denys.domain.Task;
import br.com.denys.domain.User;

import lombok.Getter;

@Getter
public class TaskDTO {

    private String title;
    private String description;
    private User user;

    public Task transformToObject() {
        return new Task(title, description, user);
    }

}
