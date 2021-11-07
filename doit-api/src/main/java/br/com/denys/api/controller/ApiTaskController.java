package br.com.denys.api.controller;

import br.com.denys.domain.Task;
import br.com.denys.dto.TaskDTO;
import br.com.denys.repository.TaskRepository;
import br.com.denys.responseDto.TaskResponseDTO;
import br.com.denys.service.TaskService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task")
public class ApiTaskController {

    private final TaskService taskService;

    public ApiTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> save(@RequestBody @Valid TaskDTO taskDTO, UriComponentsBuilder builder) {
        Task task = taskService.save(taskDTO.transformToObject());
        URI uri = builder.path("/api/task/{id}")
                .buildAndExpand(task.getId())
                .toUri();
        return ResponseEntity.created(uri).body(TaskResponseDTO.transformToResponseDTO(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> showById(@PathVariable Long id) {
        Task task = taskService.findOneById(id);
        return ResponseEntity.ok(TaskResponseDTO.transformToResponseDTO(task));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> showAllByUser(@PathVariable Long userId) {
        List<Task> tasks = taskService.findAllByUserId(userId);
        List<TaskResponseDTO> tasksDTO = tasks.stream().map(TaskResponseDTO::transformToResponseDTO).collect(Collectors.toList());
        return ResponseEntity.ok(tasksDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id, @RequestBody TaskDTO newTaskDTO) {
        Task task = taskService.update(id, newTaskDTO.transformToObject());
        return ResponseEntity.ok(TaskResponseDTO.transformToResponseDTO(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
