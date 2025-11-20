package com.fabiandahlin.taskflow.task.dto;

import com.fabiandahlin.taskflow.task.Priority;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * DTO returned to clients when sending Task data.
 * We control exactly what fields are exposed in the API.
 */
@Value           // immutable DTO
@Builder
public class TaskResponse {

    Long id;
    String title;
    String description;
    boolean completed;
    Priority priority;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
