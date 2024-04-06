package pers.terry.springpracticeaftercourse.dto;

import jakarta.validation.constraints.NotBlank;

public record UserQueryDTO(@NotBlank String account) {}
