package com.tokioschool.flight.app.domain;

import com.tokioschool.flight.app.core.repository.TsidGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id @TsidGenerator private String id;
  @CreationTimestamp
  private LocalDateTime created;
  private String name;
  private String surname;
  private String email;
  private String password;
  private LocalDateTime lastLogin;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "users_with_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

}
