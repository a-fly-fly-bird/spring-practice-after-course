package pers.terry.springpracticeaftercourse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import pers.terry.springpracticeaftercourse.enums.UserRoleEnum;

@Builder
public record UserDto(
        @NotBlank String name,
        @NotBlank @Email String email,
        @Max(150) @Min(0) Integer age,
        @Size(min = 8, max = 30) String password,
        UserRoleEnum userRole) {
}
