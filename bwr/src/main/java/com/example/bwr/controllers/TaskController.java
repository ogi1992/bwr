package com.example.bwr.controllers;

import com.example.bwr.models.Task;
import com.example.bwr.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @PostMapping
  public ResponseEntity<Task> uploadTask(@Valid @RequestBody Task task) {
    return ResponseEntity.ok(taskService.uploadTask(task));
  }

  @PostMapping("/{taskId}/users/{userId}/start")
  public ResponseEntity<Void> startCommand(@PathVariable("taskId") Integer taskId, @PathVariable("userId") Integer userId) {
    taskService.startCommand(taskId, userId);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/{taskId}/users/{userId}/stop")
  public ResponseEntity<Void> stopCommand(@PathVariable("taskId") Integer taskId, @PathVariable("userId") Integer userId) {
    taskService.stopCommand(taskId, userId);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/{taskId}/users/{userId}/end")
  public ResponseEntity<Void> endTask(@PathVariable("taskId") Integer taskId, @PathVariable("userId") Integer userId) {
    taskService.endTask(taskId, userId);

    return ResponseEntity.ok().build();
  }
}
