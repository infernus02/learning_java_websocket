package com.project.webchatbe.dto.forgot_pass.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPassRequest {
    String token;
    String newPassword;
}
