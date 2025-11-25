package com.project.webchatbe.service.imp;

import com.project.webchatbe.configuration.security.UserDetailsImpl;
import com.project.webchatbe.configuration.security.jwtConfig.JwtProvider;
import com.project.webchatbe.dto.authen.request.ChangePassRequest;
import com.project.webchatbe.dto.authen.request.ChangeProfileRequest;
import com.project.webchatbe.dto.authen.request.LoginRequest;
import com.project.webchatbe.dto.authen.request.RegisterRequest;
import com.project.webchatbe.dto.authen.response.LoginResponse;
import com.project.webchatbe.dto.user.response.UserResponse;
import com.project.webchatbe.entity.Role;
import com.project.webchatbe.entity.User;
import com.project.webchatbe.enums.RoleEnum;
import com.project.webchatbe.exception.AppException;
import com.project.webchatbe.exception.ErrorCode;
import com.project.webchatbe.mapper.UserMapper;
import com.project.webchatbe.repository.IUserRepository;
import com.project.webchatbe.service.interfaces.IAuthenService;
import com.project.webchatbe.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenService implements IAuthenService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    IUserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginResponse register(RegisterRequest request) {
        if(userService.exitsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USERNAME_WAS_REGISTER);

        Role role = new Role(RoleEnum.USER.name());
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        return LoginResponse.builder()
                .username(request.getUsername())
                .role(role.getName())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtProvider.generateTokenByUsername(request.getUsername());

        return LoginResponse.builder()
                .token(jwt)
                .role(userDetails.getRoleName())
                .username(userDetails.getUsername())
                .build();
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username == null)
            throw new AppException(ErrorCode.UNAUTHEN);

        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserResponse changeProfile(ChangeProfileRequest request) {
        User user = this.getCurrentUser();
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setPhone(request.getPhone());
        user.setAvatar(request.getAvatar());
        user.setAddress(request.getAddress());

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse changePassword(ChangePassRequest request) {
        if(request.getNewPass() == null || request.getNewPass().isEmpty())
            throw new AppException(ErrorCode.PASSWORD_NOT_NULL);

        User user = this.getCurrentUser();
        if(!passwordEncoder.matches(request.getOldPass(), user.getPassword()))
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);

        user.setPassword(passwordEncoder.encode(request.getNewPass()));

        return userMapper.toResponse(userRepository.save(user));
    }
}
