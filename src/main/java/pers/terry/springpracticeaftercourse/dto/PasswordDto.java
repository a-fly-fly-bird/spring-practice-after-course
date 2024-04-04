package pers.terry.springpracticeaftercourse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
  // Built-in Constraint definitions: https://jakarta.ee/specifications/bean-validation/3.0/jakarta-bean-validation-spec-3.0.html#builtinconstraints
  @NotBlank(message = "password为必传字段")
  private String password;
}