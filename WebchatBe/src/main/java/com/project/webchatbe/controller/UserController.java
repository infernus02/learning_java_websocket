package com.project.webchatbe.controller;

import com.project.webchatbe.dto.user.request.UserRequest;
import com.project.webchatbe.dto.user.request.UserSearch;
import com.project.webchatbe.dto.user.request.UserUpdateRequest;
import com.project.webchatbe.dto.user.response.UserResponse;
import com.project.webchatbe.exception.ApiResponse;
import com.project.webchatbe.service.interfaces.IUserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/find-all")
    ApiResponse<Page<UserResponse>> findAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                      @RequestParam(name = "limit", defaultValue = "5") Integer limit,
                                      @RequestParam(name = "keyword", required = false) String keyword){

        Pageable pageable = PageRequest.of(page-1, limit);
        UserSearch userSearch = UserSearch.builder()
                .keyword(keyword)
                .build();

        return ApiResponse.<Page<UserResponse>>builder()
                .data(userService.findAll(userSearch, pageable))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> findById(@PathVariable Long id){
        return ApiResponse.<UserResponse>builder()
                .data(userService.findById(id))
                .build();
    }

    @PostMapping("/create")
    ApiResponse<UserResponse> create(@RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .data(userService.create(request))
                .build();
    }

    @PutMapping("/update/{id}")
    ApiResponse<UserResponse> findById(@PathVariable Long id, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .data(userService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> delete(@PathVariable Long id){
        userService.delete(id);

        return ApiResponse.<Void>builder()
                .build();
    }
}
