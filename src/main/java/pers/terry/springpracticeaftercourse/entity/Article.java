package pers.terry.springpracticeaftercourse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "article")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = true)
  private String description;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private LocalDateTime createdTime;

  @Column(nullable = false)
  private LocalDateTime updateTime;

  @ManyToOne()
  @JsonBackReference
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
