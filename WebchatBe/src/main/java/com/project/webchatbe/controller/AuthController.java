package com.project.webchatbe.controller;

import com.project.webchatbe.dto.authen.request.ChangePassRequest;
import com.project.webchatbe.dto.authen.request.ChangeProfileRequest;
import com.project.webchatbe.dto.authen.request.LoginRequest;
import com.project.webchatbe.dto.authen.request.RegisterRequest;
import com.project.webchatbe.dto.authen.response.LoginResponse;
import com.project.webchatbe.dto.forgot_pass.request.EmailResetRequest;
import com.project.webchatbe.dto.forgot_pass.request.ResetPassRequest;
import com.project.webchatbe.dto.user.response.UserResponse;
import com.project.webchatbe.exception.ApiResponse;
import com.project.webchatbe.service.interfaces.IAuthenService;
import com.project.webchatbe.service.interfaces.IEmailService;
import com.project.webchatbe.service.interfaces.ITokenResetPassService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    IAuthenService authenService;
    @Autowired
    IEmailService emailService;
    @Autowired
    ITokenResetPassService tokenResetPassService;

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody LoginRequest request){
        return ApiResponse.<LoginResponse>builder()
                .data(authenService.login(request))
                .build();
    }

    @PostMapping("/register")
    ApiResponse<LoginResponse> register(@RequestBody RegisterRequest request){
        return ApiResponse.<LoginResponse>builder()
                .data(authenService.register(request))
                .build();
    }

    @PutMapping("/change-profile")
    ApiResponse<UserResponse> changeProfile(@RequestBody ChangeProfileRequest request){
        return ApiResponse.<UserResponse>builder()
                .data(authenService.changeProfile(request))
                .build();
    }

    @PutMapping("/change-password")
    ApiResponse<UserResponse> changePass(@RequestBody ChangePassRequest request){
        return ApiResponse.<UserResponse>builder()
                .data(authenService.changePassword(request))
                .build();
    }

    @PostMapping("/forgot-pass")
    ApiResponse<Void> forgotPass(@RequestBody EmailResetRequest request){
        tokenResetPassService.forgotPassword(request.getEmail());

        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/reset-pass")
    ApiResponse<Void> resetPass(@RequestBody ResetPassRequest request){
        tokenResetPassService.resetPassword(request);

        return ApiResponse.<Void>builder()
                .build();
    }
}
