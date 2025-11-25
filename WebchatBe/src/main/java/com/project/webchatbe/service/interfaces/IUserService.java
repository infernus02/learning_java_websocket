package com.project.webchatbe.service.interfaces;

import com.project.webchatbe.configuration.security.UserDetailsImpl;
import com.project.webchatbe.dto.user.request.ChangeRoleRequest;
import com.project.webchatbe.dto.user.request.UserRequest;
import com.project.webchatbe.dto.user.request.UserSearch;
import com.project.webchatbe.dto.user.request.UserUpdateRequest;
import com.project.webchatbe.dto.user.response.UserResponse;
import com.project.webchatbe.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    long count();
    UserDetailsImpl getUserInContext();

    boolean exitsByUsername(String username);

    User findByUserName(String username);
    User findByEmail(String email);

    User findEntityById(Long id);
    Page<UserResponse> findAll(UserSearch userSearch, Pageable pageable);
    UserResponse findById(Long id);
    UserResponse create(UserRequest request);
    UserResponse update(Long id, UserUpdateRequest request);
    UserResponse changeRole(Long id, ChangeRoleRequest request);
    void delete(Long id);
}
