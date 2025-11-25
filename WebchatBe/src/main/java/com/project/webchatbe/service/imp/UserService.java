package com.project.webchatbe.service.imp;

import com.project.webchatbe.configuration.security.UserDetailsImpl;
import com.project.webchatbe.dto.user.request.ChangeRoleRequest;
import com.project.webchatbe.dto.user.request.UserRequest;
import com.project.webchatbe.dto.user.request.UserSearch;
import com.project.webchatbe.dto.user.request.UserUpdateRequest;
import com.project.webchatbe.dto.user.response.UserResponse;
import com.project.webchatbe.entity.Role;
import com.project.webchatbe.entity.User;
import com.project.webchatbe.exception.AppException;
import com.project.webchatbe.exception.ErrorCode;
import com.project.webchatbe.mapper.UserMapper;
import com.project.webchatbe.repository.IRoleRepository;
import com.project.webchatbe.repository.IUserRepository;
import com.project.webchatbe.service.interfaces.IRoleService;
import com.project.webchatbe.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRoleRepository roleRepository;
    @Autowired
    IRoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        return new UserDetailsImpl(user);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public UserDetailsImpl getUserInContext() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            if (userDetails instanceof UserDetailsImpl)
                return (UserDetailsImpl) userDetails;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean exitsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
    }

    @Override
    public User findEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_FOUND)
        );
    }

    @Override
    public Page<UserResponse> findAll(UserSearch userSearch, Pageable pageable) {
        return userRepository.findAll(userSearch.getKeyword(), pageable).map(it -> userMapper.toResponse(it));
    }

    @Override
    public UserResponse findById(Long id) {
        return userMapper.toResponse(this.findEntityById(id));
    }

    @Override
    public UserResponse create(UserRequest request) {
        User user = userMapper.toEntity(request);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        User user = this.findEntityById(id);

        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setPhone(request.getPhone());
        user.setAvatar(request.getAvatar());
        user.setAvatar(request.getAddress());

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse changeRole(Long id, ChangeRoleRequest request) {
        Role role = roleService.findByName(request.getName());
        User user = this.findEntityById(id);

        user.setRole(role);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = this.findEntityById(id);
        user.setIsDelete(true);
        userRepository.save(user);
    }

}
