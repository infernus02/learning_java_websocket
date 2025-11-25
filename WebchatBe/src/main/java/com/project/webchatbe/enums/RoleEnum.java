package com.project.webchatbe.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RoleEnum {
    ADMIN,
    USER

    ;

    public static List<String> roleList(){
        List<String> roleList = Arrays.stream(RoleEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return roleList;
    }
}
