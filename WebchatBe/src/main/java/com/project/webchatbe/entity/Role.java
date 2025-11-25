package com.project.webchatbe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_role")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @Column(name = "name", unique = true, nullable = false)
    String name;

    String description;

    @OneToMany(mappedBy = "role")
    List<User> users;

    public Role(String name){
        this.name = name;
    }
}
