package pers.terry.springpracticeaftercourse.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pers.terry.springpracticeaftercourse.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  public Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  void deleteByEmail(String email);
}
