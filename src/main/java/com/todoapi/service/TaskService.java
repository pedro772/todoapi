package com.todoapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapi.DTOs.TaskRecordDTO;
import com.todoapi.model.Task;
import com.todoapi.repository.TaskRepository;

@Service
public class TaskService {
  @Autowired
  TaskRepository taskRepository;

  public Task save(TaskRecordDTO taskRecordDTO) {
    var task = new Task();
    BeanUtils.copyProperties(taskRecordDTO, task);
    return taskRepository.save(task);
  }
}
