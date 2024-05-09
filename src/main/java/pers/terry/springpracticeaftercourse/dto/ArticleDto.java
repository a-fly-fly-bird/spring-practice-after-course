package pers.terry.springpracticeaftercourse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleDto {
  @NotBlank
  private String title;
  @NotBlank
  private String description;
  @NotBlank
  private String content;
}
