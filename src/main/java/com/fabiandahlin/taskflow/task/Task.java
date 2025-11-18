package com.fabiandahlin.taskflow.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents a Task entity stored in the database.
 * Lombok annotations are used to reduce boilerplate code for:
 * - constructors
 * - getters and setters
 * - builder pattern support
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private boolean completed;
}
