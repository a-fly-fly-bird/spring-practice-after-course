package pers.terry.springpracticeaftercourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.terry.springpracticeaftercourse.entity.Article;

import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
