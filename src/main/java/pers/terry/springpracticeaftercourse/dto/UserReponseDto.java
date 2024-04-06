package pers.terry.springpracticeaftercourse.dto;

import lombok.Builder;
import pers.terry.springpracticeaftercourse.entity.User;

@Builder
public record UserReponseDto(String name, String email, Boolean isDeleted) {
  public static UserReponseDto from(User user) {
    return UserReponseDto.builder()
        .name(user.getUsername())
        .email(user.getEmail())
        .isDeleted(user.getIsDeleted())
        .build();
  }
}
