package com.project.webchatbe.configuration.init;

import com.project.webchatbe.entity.Role;
import com.project.webchatbe.entity.User;
import com.project.webchatbe.enums.RoleEnum;
import com.project.webchatbe.repository.IUserRepository;
import com.project.webchatbe.service.interfaces.IRoleService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationInitConfig {
    @Autowired
    IRoleService roleService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(){
        return args ->{
            this.createRoles();
            this.createAccountAdmin();
        };
    }

    void createRoles(){
        List<String> roleList = RoleEnum.roleList();
        for(String roleName: roleList){
            if(roleService.exitsByName(roleName) == false){
                roleService.create(roleName);
            }
        }
    }

    void createAccountAdmin(){
        if (userRepository.count() == 0){
            Role roleAdmin = new Role(RoleEnum.ADMIN.name());

            User user = User.builder()
                    .username("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .role(roleAdmin)
                    .build();

            userRepository.save(user);
        }
    }
}
