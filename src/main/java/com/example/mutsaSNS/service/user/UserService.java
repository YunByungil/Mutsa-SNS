package com.example.mutsaSNS.service.user;

import com.example.mutsaSNS.domain.entity.user.User;
import com.example.mutsaSNS.domain.repository.user.UserRepository;
import com.example.mutsaSNS.dto.user.request.UserJoinRequestDto;
import com.example.mutsaSNS.dto.user.request.UserUpdateRequestDto;
import com.example.mutsaSNS.dto.user.response.UserJoinResponseDto;
import com.example.mutsaSNS.dto.user.response.UserUpdateResponseDto;
import com.example.mutsaSNS.exception.ErrorCode;
import com.example.mutsaSNS.exception.MutsaSnsAppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.mutsaSNS.exception.ErrorCode.*;

@Slf4j
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

    @Transactional
    public UserUpdateResponseDto updateUser(UserUpdateRequestDto updateDto,
                                            final Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        String profileDir = String.format("profile/%d/", userId);

        deleteFilesInProfileDirectory(profileDir);

        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            log.error("IOException = {}", e);
            throw new RuntimeException();
        }

        String profileFilename = generateProfileFilename(updateDto);

        String profilePath = profileDir + profileFilename;
        Path path = Path.of(profilePath);

        try {
            updateDto.getImage().transferTo(path);
        } catch (IOException e) {
            log.error("IOException = {}", e);
            throw new RuntimeException();
        }

        String image = String.format("/static/%d/%s", userId, profileFilename);
        user.updateUser(updateDto, passwordEncoder.encode(updateDto.getPassword()), image);

        return new UserUpdateResponseDto(user);
    }
    @Transactional
    public UserUpdateResponseDto updateImage(MultipartFile updateDto,
                                            final Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new MutsaSnsAppException(NOT_FOUND_USER, NOT_FOUND_USER.getMessage()));

        String profileDir = String.format("profile/%d/", userId);
        deleteFilesInProfileDirectory(profileDir);

        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) {
            log.error("IOException = {}", e);
            throw new RuntimeException();
        }

        String profileFilename = generateProfileFilenameOnlyImage(updateDto);

        String profilePath = profileDir + profileFilename;
        Path path = Path.of(profilePath);

        try {
            updateDto.transferTo(path);
        } catch (IOException e) {
            log.error("IOException = {}", e);
            throw new RuntimeException();
        }

        user.updateUserOnlyImage(String.format("/static/%d/%s", userId, profileFilename));

        return new UserUpdateResponseDto(user);
    }

    private String generateProfileFilenameOnlyImage(MultipartFile updateDto) {
        String originalFilename = updateDto.getOriginalFilename();

        String[] fileNameSplit = originalFilename.split("\\.");

        String extension = fileNameSplit[fileNameSplit.length - 1];
        return "profile." + extension;
    }
    private String generateProfileFilename(UserUpdateRequestDto updateDto) {
        String originalFilename = updateDto.getImage().getOriginalFilename();

        String[] fileNameSplit = originalFilename.split("\\.");

        String extension = fileNameSplit[fileNameSplit.length - 1];
        return "profile." + extension;
    }

    private void deleteFilesInProfileDirectory(String profileDir) {
        File directory = new File(profileDir);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    private void validateDuplicateUsername(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new MutsaSnsAppException(ALREADY_USER_USERNAME, ALREADY_USER_USERNAME.getMessage());
                });
    }
}
