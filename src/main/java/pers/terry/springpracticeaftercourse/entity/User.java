package pers.terry.springpracticeaftercourse.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "user")
public class User {

  @Id
  @Column(nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.UUID)
  // 落库到数据库，因为是binary(16)类型，所以显示乱码是正常的。Java程序输出时会自动把UUID转化为String。
  // public String toString() {
  // return jla.fastUUID(this.leastSigBits, this.mostSigBits);
  // }
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
