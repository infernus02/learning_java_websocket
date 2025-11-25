package com.project.webchatbe.service.imp;

import com.project.webchatbe.entity.Role;
import com.project.webchatbe.exception.AppException;
import com.project.webchatbe.exception.ErrorCode;
import com.project.webchatbe.repository.IRoleRepository;
import com.project.webchatbe.service.interfaces.IRoleService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public boolean exitsByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public void create(String name) {
        Role entity = Role.builder()
                .name(name)
                .build();

        roleRepository.save(entity);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }
}
