package br.com.denys.responseDto;

import br.com.denys.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Long userId;

    public static TaskResponseDTO transformToResponseDTO(Task task) {
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.getDescription(), task.getUser().getId());
    }

}
