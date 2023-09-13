package com.todoapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapi.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
}
