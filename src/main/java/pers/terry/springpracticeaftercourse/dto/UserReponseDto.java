package pers.terry.springpracticeaftercourse.dto;


import lombok.Builder;

@Builder
public record UserReponseDto(String name, String email) {
}
