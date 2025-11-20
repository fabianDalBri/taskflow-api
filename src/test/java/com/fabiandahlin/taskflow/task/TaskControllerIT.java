package com.fabiandahlin.taskflow.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fabiandahlin.taskflow.task.dto.TaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test for TaskController using DTOs.
 * This verifies full serialization/deserialization via TaskRequest and TaskResponse.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void createTask_usesDTOs_andPersistsToDatabase() throws Exception {
        // Clean DB before test
        taskRepository.deleteAll();

        // Build the DTO we want to send in the request body
        TaskRequest request = new TaskRequest();
        request.setTitle("Integration Test Task");
        request.setDescription("Created through integration test");
        request.setCompleted(false);
        request.setPriority(Priority.LOW);

        // Convert DTO -> JSON
        String jsonBody = objectMapper.writeValueAsString(request);

        // Perform POST /tasks with JSON body
        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody)
                )
                .andExpect(status().isOk()) // Or .isCreated() if you change controller later
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Integration Test Task"))
                .andExpect(jsonPath("$.description").value("Created through integration test"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.priority").value("LOW"));

        // Optional: verify DB side effects
        assertEquals(1, taskRepository.count());
    }
}
