package com.project.webchatbe.service.interfaces;

import com.project.webchatbe.dto.authen.request.ChangePassRequest;
import com.project.webchatbe.dto.authen.request.ChangeProfileRequest;
import com.project.webchatbe.dto.authen.request.LoginRequest;
import com.project.webchatbe.dto.authen.request.RegisterRequest;
import com.project.webchatbe.dto.authen.response.LoginResponse;
import com.project.webchatbe.dto.user.response.UserResponse;
import com.project.webchatbe.entity.User;

public interface IAuthenService {
    LoginResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);

    User getCurrentUser();
    UserResponse changeProfile(ChangeProfileRequest request);
    UserResponse changePassword(ChangePassRequest request);
}
