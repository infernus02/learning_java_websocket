package com.project.webchatbe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(-1, "Lỗi không xác định"),
    UNAUTHEN(401, "Chưa đăng nhập"),
    FORBIDDEN(403, "Không có quyền truy cập"),

    //---user exception
    USER_NOT_FOUND(400, "Không tìm thấy user"),
    USERNAME_WAS_REGISTER(400, "Tên đăng nhập đã được sử dụng"),

    PASSWORD_NOT_NULL(400, "Mật khẩu không được để trống!"),
    PASSWORD_NOT_MATCH(400, "Mật khẩu cũ không đúng"),

    //---role exception
    ROLE_NOT_FOUND(400, "Vai trò không tồn tại!"),
    ROLE_NOT_NULL(400, "Vai trò không được để trống"),

    MAIL_CONFIG_MISSING(400, "Lỗi cấu hình email"),
    MAIL_SENDING_FAILED(400, "Gửi email thất bại"),

    TOKEN_RESET_PASS_NOT_FOUND(400, "Link không hợp lệ"),
    TOKEN_EXPIRED(400, "Link hết hạn"),
    ;

    private int code;
    private String message;
}
