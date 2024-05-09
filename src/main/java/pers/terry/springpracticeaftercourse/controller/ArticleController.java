package pers.terry.springpracticeaftercourse.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
  // authScheme 就是前面 SpringDocConfig .addSecuritySchemes("authScheme" 的 authScheme
  @Operation(summary = "添加文章", security = @SecurityRequirement(name = "authScheme"))
  public ResponseEntity<Article> addArticle(@RequestBody @Validated ArticleDto articleDto) {
    Article article = this.articleService.addArticle(articleDto);
    return ResponseEntity.ok(article);
  }

  /**
   * 分页教程：
   * <a href="https://www.baeldung.com/spring-data-jpa-pagination-sorting">...</a>
   *
   * @param page page number
   * @param size page size
   * @return A Page<T> instance, in addition to having the list of Products, also
   *         knows about the
   *         total number of available pages.
   */
  @GetMapping("/{page}/{size}")
  @Operation(summary = "分页获取文章", security = @SecurityRequirement(name = "authScheme"))
  public Page<Article> getArticles(@PathVariable Integer page, @PathVariable Integer size) {
    return this.articleService.getArticlesByPage(page, size);
  }

  @PutMapping("/")
  @Operation(summary = "编辑文章", security = @SecurityRequirement(name = "authScheme"))
  public ResponseEntity<Article> editArticle(@RequestBody Article article) {
    return ResponseEntity.ok(this.articleService.editArticle(article));
  }
}
