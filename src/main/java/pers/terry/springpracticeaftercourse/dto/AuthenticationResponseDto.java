package pers.terry.springpracticeaftercourse.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponseDto(String token) {

}
