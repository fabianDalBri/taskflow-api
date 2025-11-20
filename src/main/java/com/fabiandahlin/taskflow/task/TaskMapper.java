package com.fabiandahlin.taskflow.task;

import com.fabiandahlin.taskflow.task.dto.TaskRequest;
import com.fabiandahlin.taskflow.task.dto.TaskResponse;

/**
 * Utility class for converting between Task entities and DTOs.
 */
public final class TaskMapper {

    private TaskMapper() {
        // utility class
    }

    /**
     * Converts a TaskRequest DTO to a new Task entity.
     * Used when creating new tasks.
     */
    public static Task toEntity(TaskRequest request) {
        if (request == null) {
            return null;
        }

        Task.TaskBuilder builder = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority());

        // If completed is null, default to false
        if (request.getCompleted() != null) {
            builder.completed(request.getCompleted());
        } else {
            builder.completed(false);
        }

        return builder.build();
    }

    /**
     * Converts a Task entity to a TaskResponse DTO.
     * Used when sending data back to the client.
     */
    public static TaskResponse toResponse(Task task) {
        if (task == null) {
            return null;
        }

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .priority(task.getPriority())
                .createdAt(task.getCreatedDate())
                .updatedAt(task.getLastModifiedDate())
                .build();
    }
}
