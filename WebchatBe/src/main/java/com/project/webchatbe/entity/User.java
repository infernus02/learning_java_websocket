package com.project.webchatbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String username;

    @Column(name = "email")
    String email;

    String fullname;
    String phone;
    String avatar;
    String address;

    @Column(nullable = false)
    String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    @OneToMany(mappedBy = "user")
    List<TokenResetPass> tokenResetPasses;
}
