package com.example.mutsaSNS.domain.repository.user;

import com.example.mutsaSNS.domain.entity.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @DisplayName("findByUsername() 메서드 테스트")
    @Test
    void findByUsername() {
        // given
        final String username = "아이디";
        userRepository.save(User.builder()
                .username(username)
                .password("비밀번호")
                .build());

        // when
        User user = userRepository.findByUsername(username).get();

        // then
        assertThat(user.getUsername()).isEqualTo(username);

    }
}