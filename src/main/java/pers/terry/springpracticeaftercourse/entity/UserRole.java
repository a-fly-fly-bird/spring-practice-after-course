package pers.terry.springpracticeaftercourse.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import pers.terry.springpracticeaftercourse.enums.UserRoleEnum;

@Entity
@Data
@Builder
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
