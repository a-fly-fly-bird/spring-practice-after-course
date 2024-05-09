package pers.terry.springpracticeaftercourse.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pers.terry.springpracticeaftercourse.dto.ArticleDto;
import pers.terry.springpracticeaftercourse.entity.Article;
import pers.terry.springpracticeaftercourse.service.ArticleService;

@SpringBootTest
public class ArticleControllerTest {
  @Autowired
  private ArticleService articleService;

  @Test
  void testAddArticle() {
    var article = ArticleDto.builder().title("title").description("description").build();
    Article savedArticle = this.articleService.addArticle(article);
    assertEquals(savedArticle, article);
  }

  @Test
  void testEditArticle() {

  }

  @Test
  void testGetArticles() {

  }
}
