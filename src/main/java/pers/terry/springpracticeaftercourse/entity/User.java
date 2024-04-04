package pers.terry.springpracticeaftercourse.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

  @Id
  @Column(nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.UUID)
  // 落库到数据库，因为是binary(16)类型，所以显示乱码是正常的。Java程序输出时会自动把UUID转化为String。
  // public String toString() {
  // return jla.fastUUID(this.leastSigBits, this.mostSigBits);
  // }
  private UUID id;

  @Column(nullable = false, length = 30)
  private String username;

  @Column(length = 50, unique = true)
  private String email;

  @Column()
  private Integer age;

  @Column(length = 256, nullable = false)
  @JsonIgnore
  private String password;

  /**
   * cascade 相当于 给当前设置的实体操作另一个实体的权限.
   * Reference: <a href="https://www.jianshu.com/p/e8caafce5445">...</a> and
   * <a href="https://www.baeldung.com/jpa-cascade-types">...</a>
   * mappedBy: JPA Relationships can be either unidirectional(单向的) or
   * bidirectional（双向的）. 一般多的一方是the owning
   * side，如果要想Relationships是bidirectional（双向的），就需要在The inverse or the referencing
   * side指定map到关系的拥有方。
   * Reference: <a href="https://www.baeldung.com/jpa-joincolumn-vs-mappedby">...</a>
   */
  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<UserRole> userRoles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> auths = new ArrayList<>();
    for (UserRole userRole : userRoles) {
      auths.add(new SimpleGrantedAuthority(userRole.getRole().name()));
    }
    return auths;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", age=" + age +
            ", password='" + password + '\'';
  }
}
