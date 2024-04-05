package pers.terry.springpracticeaftercourse.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank String account, @NotBlank String password) {

}
