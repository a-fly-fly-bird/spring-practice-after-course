package pers.terry.springpracticeaftercourse.entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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

  /**
   * cascade 相当于 给当前设置的实体操作另一个实体的权限.
   * Reference: https://www.jianshu.com/p/e8caafce5445 and
   * https://www.baeldung.com/jpa-cascade-types
   * 
   * mappedBy: JPA Relationships can be either unidirectional(单向的) or
   * bidirectional（双向的）. 一般多的一方是the owning
   * side，如果要想Relationships是bidirectional（双向的），就需要在The inverse or the referencing
   * side指定map到关系的拥有方。
   * Reference: https://www.baeldung.com/jpa-joincolumn-vs-mappedby
   */
  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
  @JsonManagedReference
  private List<UserRole> userRoles;
}
