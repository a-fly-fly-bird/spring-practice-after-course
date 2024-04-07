package pers.terry.springpracticeaftercourse.dto;

import lombok.Builder;
import lombok.Setter;
import pers.terry.springpracticeaftercourse.entity.User;

@Builder
public record UserReponseDto(
    String name, String email, Boolean isDeleted, Integer age, String token) {
  public static UserReponseDto from(User user) {
    return UserReponseDto.builder()
        .name(user.getUsername())
        .email(user.getEmail())
        .age(user.getAge())
        .isDeleted(user.getIsDeleted())
        .token(user.getToken())
        .build();
  }
}
