package pers.terry.springpracticeaftercourse.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Builder
@Table(name = "user")
public class User {

  @Id
  @Column(nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 30)
  private String name;

  @Column(length = 50)
  private String email;

  @Column()
  private Integer age;

  @Column(length = 50, nullable = false)
  private String password;

  @OneToMany(mappedBy = "user")
  @JsonManagedReference
  private List<UserRole> userRoles;
}
