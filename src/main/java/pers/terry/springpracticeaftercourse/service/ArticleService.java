package pers.terry.springpracticeaftercourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.terry.springpracticeaftercourse.dto.ArticleDto;
import pers.terry.springpracticeaftercourse.entity.Article;
import pers.terry.springpracticeaftercourse.repository.ArticleRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;

  public Article addArticle(ArticleDto articleDto) {
    return this.articleRepository.save(Article.from(articleDto));
  }
}
