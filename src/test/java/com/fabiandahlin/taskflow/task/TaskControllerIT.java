package com.fabiandahlin.taskflow.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for TaskController.
 *
 * These tests start a full Spring Boot context and use MockMvc to simulate
 * real HTTP calls against the /tasks endpoint. The goal is to verify that the
 * controller, service, repository and JSON mapping all work together.
 */
@SpringBootTest            // Start the whole Spring Boot application (in-memory)
@AutoConfigureMockMvc      // Configure MockMvc so we can perform HTTP requests in tests
class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;          // Used to perform fake HTTP requests

    @Autowired
    private ObjectMapper objectMapper; // Used to convert Java objects <-> JSON

    @Autowired
    private TaskRepository taskRepository; // Real repository backed by H2 in-memory DB

    @Test
    void createTask_returnsOk_andPersistsInDatabase() throws Exception {
        // Arrange: ensure database is empty before test (clean state)
        taskRepository.deleteAll();

        // Build a Task object that represents the JSON body we want to send
        Task task = Task.builder()
                .title("Integration test task")
                .description("Created from integration test")
                .completed(false)
                .priority(Priority.LOW)
                .build();

        // Convert the Task object into a JSON string
        String jsonBody = objectMapper.writeValueAsString(task);

        // Act + Assert: perform HTTP POST /tasks with the JSON body
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                // Expect HTTP 200 OK (or change to isCreated() if your controller returns 201)
                .andExpect(status().isOk())
                // Response should be JSON
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // Verify that the response JSON contains the same title
                .andExpect(jsonPath("$.title").value("Integration test task"))
                // Verify that the response has an id field (meaning it was saved)
                .andExpect(jsonPath("$.id").isNumber());

        // Extra assertion: verify that exactly one task exists in the database now
        long count = taskRepository.count();
        assertEquals(1, count, "Exactly one Task should have been persisted in the database");
    }
}
