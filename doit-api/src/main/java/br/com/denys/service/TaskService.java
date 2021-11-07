package br.com.denys.service;

import br.com.denys.domain.Task;
import br.com.denys.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task findOneById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()) {
            throw new Error("Task não encontrada");
        }

        return task.get();
    }

    public List<Task> findAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    public Task update(Long id, Task newTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if(optionalTask.isEmpty()) throw new Error("Task não encontrada");

        Task task = optionalTask.get();
        newTask.setId(task.getId());
        return taskRepository.save(newTask);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
