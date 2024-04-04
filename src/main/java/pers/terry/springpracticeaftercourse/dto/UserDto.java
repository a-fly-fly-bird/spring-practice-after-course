package pers.terry.springpracticeaftercourse.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import pers.terry.springpracticeaftercourse.enums.UserRoleEnum;

@Builder
public record UserDto(@NotBlank String name, @Email String email, @Max(150) @Min(0) Integer age, @Size(min = 8, max = 30) String password, UserRoleEnum userRole) {

}
