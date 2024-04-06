package pers.terry.springpracticeaftercourse.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pers.terry.springpracticeaftercourse.dto.ArticleDto;
import pers.terry.springpracticeaftercourse.entity.Article;
import pers.terry.springpracticeaftercourse.entity.User;
import pers.terry.springpracticeaftercourse.repository.ArticleRepository;
import pers.terry.springpracticeaftercourse.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;

  private final UserRepository userRepository;

  public Article addArticle(ArticleDto articleDto) {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    Optional<User> userOptional = this.userRepository.findByEmail(username);
    return userOptional
        .map(
            user -> {
              Article article = Article.from(articleDto);
              article.setUser(user);
              return this.articleRepository.save(article);
            })
        .get();
  }
}
