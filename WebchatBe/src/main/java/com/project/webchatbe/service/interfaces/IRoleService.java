package com.project.webchatbe.service.interfaces;

import com.project.webchatbe.entity.Role;

public interface IRoleService {
    boolean exitsByName(String name);
    void create(String name);

    Role findByName(String name);
}
