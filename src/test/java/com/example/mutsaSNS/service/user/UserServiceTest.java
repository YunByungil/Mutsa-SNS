package com.example.mutsaSNS.service.user;

import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.user.request.UserJoinRequestDto;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @DisplayName("회원가입 서비스#createUser() 테스트")
    @Test
    void createUser() {
        // given
        final String username = "아이디";
        final String password = "비밀번호";
        UserJoinRequestDto joinDto = createUserJoinDtoWithUsernameAndPassword(username, password);

        // when
        userService.createUser(joinDto);
        User findUser = userRepository.findAll().get(0);

        // then
        assertThat(findUser.getUsername()).isEqualTo(username);
    }

    @DisplayName("회원가입 서비스 중복 검사#validateDuplicateUsername() 테스트")
    @Test
    void validateDuplicateUsername() {
        // given
        final String username = "아이디";
        final String password = "비밀번호";
        UserJoinRequestDto joinDto = createUserJoinDtoWithUsernameAndPassword(username, password);

        // when
        userService.createUser(joinDto);

        // then
        assertThatThrownBy(() -> {
            userService.createUser(joinDto);
        }).isInstanceOf(MutsaSnsAppException.class);
    }

    private UserJoinRequestDto createUserJoinDtoWithUsernameAndPassword(final String username, final String password) {
        return UserJoinRequestDto.builder()
                .username(username)
                .password(password)
                .build();
    }
}