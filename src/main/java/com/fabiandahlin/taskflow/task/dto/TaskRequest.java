package com.fabiandahlin.taskflow.task.dto;

import com.fabiandahlin.taskflow.task.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO used when creating or fully updating a Task.
 * This represents the data sent by the client in the request body.
 */
@Data
public class TaskRequest {

    @NotBlank(message = "Title must not be blank")
    private String title;

    private String description;

    // Nullable so client can choose to omit it and let the backend set defaults.
    private Boolean completed;

    private Priority priority;
}
