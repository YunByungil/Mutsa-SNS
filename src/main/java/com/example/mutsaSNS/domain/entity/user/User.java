package com.example.mutsaSNS.domain.entity.user;

import com.example.mutsaSNS.domain.entity.BaseTimeEntity;
import com.example.mutsaSNS.domain.entity.enums.Role;
import com.example.mutsaSNS.dto.user.request.UserUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

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

    public void updateUser(final UserUpdateRequestDto dto, final String password, final String image) {
        this.username = dto.getUsername();
        this.password = password;
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.image = image;
    }
    public void updateUserOnlyImage(final String dto) {
        this.image = dto.toString();
    }
}
