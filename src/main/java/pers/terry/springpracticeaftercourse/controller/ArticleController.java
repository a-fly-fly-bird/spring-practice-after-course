package pers.terry.springpracticeaftercourse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.terry.springpracticeaftercourse.dto.ArticleDto;
import pers.terry.springpracticeaftercourse.entity.Article;
import pers.terry.springpracticeaftercourse.service.ArticleService;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
@Tag(name = "文章", description = "文章增删改查")
public class ArticleController {
  private final ArticleService articleService;

  @PostMapping("/")
  @Operation(summary = "添加文章")
  public ResponseEntity<ArticleDto> addArticle(@RequestBody @Validated ArticleDto articleDto) {
    Article article = this.articleService.addArticle(articleDto);
    return ResponseEntity.ok(articleDto);
  }
}
