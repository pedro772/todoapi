package com.todoapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.todoapi.DTOs.TaskRecordDTO;
import com.todoapi.model.Task;
import com.todoapi.repository.TaskRepository;
import com.todoapi.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {
  @Autowired
  TaskRepository taskRepository;

  @Autowired
  TaskService taskService;

  @PostMapping("/task")
  @ResponseStatus(HttpStatus.CREATED)
  public Task saveTask(@RequestBody @Valid TaskRecordDTO taskRecordDTO) {
    return taskService.save(taskRecordDTO);
  }

  @GetMapping("/task")

  public ResponseEntity<List<Task>> getAllTasks() {
    List<Task> taskList = taskRepository.findAll();

    if (!taskList.isEmpty()) {
      for (Task task : taskList) {
        UUID id = task.getIdTask();
        task.add(linkTo(methodOn(TaskController.class).getTaskById(id)).withSelfRel());
      }
    }

    return ResponseEntity.status(HttpStatus.OK).body(taskList);
  }

  @GetMapping("/task/{id}")
  public ResponseEntity<Object> getTaskById(@PathVariable(value = "id") UUID id) {
    Optional<Task> taskO = taskRepository.findById(id);

    if (taskO.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
    }

    taskO.get().add(linkTo(methodOn(TaskController.class).getAllTasks()).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(taskO.get());
  }

  @PutMapping("/task/{id}")
  public ResponseEntity<Object> updateTask(@PathVariable(value = "id") UUID id,
      @RequestBody @Valid TaskRecordDTO taskRecordDTO) {
    Optional<Task> taskO = taskRepository.findById(id);

    if (taskO.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
    }

    var task = taskO.get();
    BeanUtils.copyProperties(taskRecordDTO, task);
    return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(task));
  }

  @DeleteMapping("/task/{id}")
  public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") UUID id) {
    Optional<Task> taskO = taskRepository.findById(id);

    if (taskO.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
    }

    taskRepository.delete(taskO.get());
    return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully!");
  }
}
