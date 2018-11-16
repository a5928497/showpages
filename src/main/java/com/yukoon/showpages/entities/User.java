package com.yukoon.showpages.entities;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class User {

    @GeneratedValue
    @Id
    private Integer id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "TITLE")
    private String title;

    @JoinColumn(name = "ROLE_ID")
    @OneToOne
    private Role role;

}
