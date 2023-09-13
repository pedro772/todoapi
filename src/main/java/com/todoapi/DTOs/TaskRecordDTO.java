package com.todoapi.DTOs;

import jakarta.validation.constraints.NotBlank;

public record TaskRecordDTO(@NotBlank String name, boolean isDone) {
}
