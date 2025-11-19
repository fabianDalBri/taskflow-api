package com.fabiandahlin.taskflow.task;

/**
 * Custom exception thrown when a Task with a given ID is not found.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Task not found with id: " + id);
    }
}
