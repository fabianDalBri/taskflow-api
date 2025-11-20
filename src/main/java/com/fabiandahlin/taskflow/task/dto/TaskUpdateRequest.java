package com.fabiandahlin.taskflow.task.dto;

import com.fabiandahlin.taskflow.task.Priority;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO used for partial updates of a Task.
 * All fields are nullable so we can check which ones should be updated.
 */
@Getter
@Setter
public class TaskUpdateRequest {

    private String title;
    private String description;
    private Boolean completed;   // Boolean (wrapper) so it can be null
    private Priority priority;   // can also be null

}
