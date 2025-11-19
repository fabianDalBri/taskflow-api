package com.fabiandahlin.taskflow.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer responsible for handling business logic around tasks.
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository; //this is the DB

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTask(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    public Task createTask(Task task) {
        return repository.save(task);
    }

    public Task updateTask(Long id, Task updated) {
        Task existing = getTask(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.isCompleted());
        existing.setPriority(updated.getPriority());
        return repository.save(existing);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    public Task markTaskAsCompleted(Long id) {
        Task task = getTask(id);
        task.setCompleted(true);
        return repository.save(task);
    }

    public Task markTaskAsUncompleted(Long id) {
        Task task = getTask(id);
        task.setCompleted(false);
        return repository.save(task);
    }

    public Task partialUpdateTask(Long id, TaskUpdateRequest request) {
        Task existing = getTask(id);

        if (request.getTitle() != null) {
            existing.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }

        if (request.getCompleted() != null) {
            existing.setCompleted(request.getCompleted());
        }

        if (request.getPriority() != null) {
            existing.setPriority(request.getPriority());
        }

        return repository.save(existing);
    }

    public List<Task> searchTasks(String query) {
        return repository.findByTitleContainingIgnoreCase(query);
    }
}
