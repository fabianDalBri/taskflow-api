package com.fabiandahlin.taskflow.task;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes CRUD endpoints for Task operations.
 * POST   /tasks      -> create a new task
 * GET    /tasks      -> get all tasks
 * GET    /tasks/{id} -> get a specific task
 * PUT    /tasks/{id} -> update a task
 * DELETE /tasks/{id} -> delete a task
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public List<Task> getAll() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getOne(@PathVariable Long id) {
        return service.getTask(id);
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return service.createTask(task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return service.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTask(id);
    }
}
