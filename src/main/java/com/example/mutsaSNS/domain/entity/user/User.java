package com.example.mutsaSNS.domain.entity.user;

import com.example.mutsaSNS.domain.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String username, String password, String email, String phone, String image, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.role = role;
    }
}
