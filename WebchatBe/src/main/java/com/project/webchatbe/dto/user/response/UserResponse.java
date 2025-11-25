package com.project.webchatbe.dto.user.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String email;
    String fullname;
    String phone;
    String avatar;
    String address;
    String password;
    String role;
}
