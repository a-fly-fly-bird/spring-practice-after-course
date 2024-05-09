package pers.terry.springpracticeaftercourse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank @Email String account, @NotBlank String password) {

}
