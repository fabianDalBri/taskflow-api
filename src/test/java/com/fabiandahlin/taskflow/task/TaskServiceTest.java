package com.fabiandahlin.taskflow.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TaskService.
 *
 * These tests focus only on the business logic inside TaskService.
 * The TaskRepository is mocked, so no real database or Spring context is used.
 */
@ExtendWith(MockitoExtension.class)   // Use Mockito's JUnit 5 extension for @Mock/@InjectMocks
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;   // Mocked dependency

    @InjectMocks
    private TaskService taskService;         // Class under test (with mocked repository injected)

    @Test
    void getTask_returnsTask_whenFound() {
        // Arrange: build a fake Task that the repository should return
        Task task = Task.builder()
                .id(1L)
                .title("Test task")
                .description("Description")
                .completed(false)
                .priority(Priority.LOW)
                .build();

        // When repository.findById(1L) is called, return Optional.of(task)
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Act: call the service method under test
        Task result = taskService.getTask(1L);

        // Assert: verify that we got the expected task back
        assertEquals("Test task", result.getTitle());
        assertEquals(1L, result.getId());

        // Verify that the repository was actually called
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTask_throwsTaskNotFoundException_whenNotFound() {
        // Arrange: repository returns empty Optional for this id
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert:
        // We expect TaskService.getTask(99L) to throw TaskNotFoundException
        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(99L));

        // Verify that repository was called once with id 99
        verify(taskRepository, times(1)).findById(99L);
    }

    @Test
    void createTask_savesTask_viaRepository() {
        // Arrange: build a new task without an id (as if it came from the client)
        Task toSave = Task.builder()
                .title("New task")
                .description("Created from test")
                .completed(false)
                .priority(Priority.MEDIUM)
                .build();

        // Simulate that the database assigns id=10L when saving
        Task saved = Task.builder()
                .id(10L)
                .title("New task")
                .description("Created from test")
                .completed(false)
                .priority(Priority.MEDIUM)
                .build();

        when(taskRepository.save(toSave)).thenReturn(saved);

        // Act: call the service
        Task result = taskService.createTask(toSave);

        // Assert: the returned object should be the "saved" one (with an id)
        assertEquals(10L, result.getId());
        assertEquals("New task", result.getTitle());

        // Verify that repository.save() was called exactly once with the right object
        verify(taskRepository, times(1)).save(toSave);
    }
}
