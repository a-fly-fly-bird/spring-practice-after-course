package pers.terry.springpracticeaftercourse.dto;

import lombok.Builder;
import pers.terry.springpracticeaftercourse.enums.UserRoleEnum;

@Builder
public record UserDto(String name, String email, Integer age, String password, UserRoleEnum userRole) {

}
