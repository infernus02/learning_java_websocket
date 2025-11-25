package com.project.webchatbe.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {
    String email;
    String fullname;
    String phone;
    String avatar;
    String address;
    String password;
}
