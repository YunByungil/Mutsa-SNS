package com.example.mutsaSNS.service.user;

import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.user.request.UserJoinRequestDto;
import com.example.mutsaSNS.dto.user.response.UserJoinResponseDto;
import com.example.mutsaSNS.exception.ErrorCode;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.mutsaSNS.exception.ErrorCode.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserJoinResponseDto createUser(final UserJoinRequestDto joinDto) {
        validateDuplicateUsername(joinDto.getUsername());

        User user = joinDto.toEntity(passwordEncoder.encode(joinDto.getPassword()));
        return new UserJoinResponseDto(userRepository.save(user));
    }

    private void validateDuplicateUsername(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new MutsaSnsAppException(ALREADY_USER_USERNAME, ALREADY_USER_USERNAME.getMessage());
                });
    }
}
