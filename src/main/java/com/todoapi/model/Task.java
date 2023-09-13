package com.todoapi.model;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Task extends RepresentationModel<Task> implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID idTask;

  private String name;

  private boolean isDone;

  public UUID getIdTask() {
    return this.idTask;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getIsDone() {
    return this.isDone;
  }

  public void setIsDone(boolean isDone) {
    this.isDone = isDone;
  }
}
