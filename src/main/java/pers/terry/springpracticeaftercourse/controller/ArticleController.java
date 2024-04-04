package pers.terry.springpracticeaftercourse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.terry.springpracticeaftercourse.dto.ArticleDto;
import pers.terry.springpracticeaftercourse.service.ArticleService;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
  private final ArticleService articleService;

  @PostMapping("/")
  public ResponseEntity<ArticleDto> addArticle(@RequestBody @Validated ArticleDto articleDto){
    return  ResponseEntity.ok(articleDto);
  }
}
