package com.project.webchatbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_token_reset_password")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResetPass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
