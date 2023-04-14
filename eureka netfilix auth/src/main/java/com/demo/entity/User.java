package com.demo.entity;

/*
    created by Atiye Mousavi
    Date: 1/1/2023
    Time: 8:04 PM
*/


import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_passwd")
    private String password;

    @Column(name = "user_email")
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "user_role")
    private Set<String> roles;

    @Transient
    private String token;

    @Transient
    private String message;

    @Transient
    private String hiddenCaptcha;

    @Transient
    private String captcha;

    @Transient
    private String realCaptcha;


}