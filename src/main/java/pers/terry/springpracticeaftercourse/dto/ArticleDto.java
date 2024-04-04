package pers.terry.springpracticeaftercourse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleDto {
  @NotBlank
  private String title;
  @NotBlank
  private String description;
  @NotBlank
  private String content;
}
