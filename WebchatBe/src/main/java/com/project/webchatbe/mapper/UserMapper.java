package com.project.webchatbe.mapper;

import com.project.webchatbe.dto.user.request.UserRequest;
import com.project.webchatbe.dto.user.response.UserResponse;
import com.project.webchatbe.entity.Role;
import com.project.webchatbe.entity.User;
import com.project.webchatbe.exception.AppException;
import com.project.webchatbe.exception.ErrorCode;
import com.project.webchatbe.service.interfaces.IRoleService;
import com.project.webchatbe.util.UtilConst;
import com.project.webchatbe.util.UtilFile;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserMapper {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    IRoleService roleService;

    public User toEntity(UserRequest request){
        if(request.getRole() == null)
            throw new AppException(ErrorCode.ROLE_NOT_NULL);
        Role role = roleService.findByName(request.getRole());

        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .fullname(request.getFullname())
                .phone(request.getPhone())
                .avatar(request.getAvatar())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
    }

    public UserResponse toResponse(User entity){
        String avatarTmp = UtilConst.DEFAULT_AVATAR_IMAGE;
        if(UtilFile.hasMediaLink(entity.getAvatar()))
            avatarTmp = entity.getAvatar();

        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .fullname(entity.getFullname())
                .phone(entity.getPhone())
                .avatar(avatarTmp)
                .address(entity.getAddress())
                .role(entity.getRole().getName())
                .build();
    }
}
