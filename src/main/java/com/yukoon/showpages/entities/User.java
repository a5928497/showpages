package com.yukoon.showpages.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class User {

    @GeneratedValue
    @Id
    private Integer id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE_ID")
    private Role role;

}
